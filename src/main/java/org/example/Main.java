package org.example;

import org.example.collection.CustomArrayList;
import org.example.exception.InvalidStudentDataException;
import org.example.model.Student;
import org.example.service.FileService;
import org.example.service.StudentService;
import org.example.service.StudentValidator;
import org.example.sort.BubbleSortAlgorithm;
import org.example.sort.QuickSortAlgorithm;
import org.example.sort.SortAlgorithm;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final CustomArrayList<Student> students = new CustomArrayList<>();
    private static final StudentService studentService = new StudentService();
    private static final FileService fileService = new FileService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Система управления списком студентов ===");
        boolean running = true;
        while (running) {
            printMenu();
            int command = readCommand();
            switch (command) {
                case 1 -> addStudentManually();
                case 2 -> loadStudentsFromFile();
                case 3 -> showAllStudents();
                case 4 -> filterStudents();
                case 5 -> mapStudents();
                case 6 -> sortStudentsViaStream();
                case 7 -> sortStudentsCustomAlgorithm();
                case 8 -> saveStudentsToFile();
                case 0 -> {
                    System.out.println("Завершение работы программы...");
                    running = false;
                }
                default -> System.out.println("Неизвестная команда. Попробуйте снова.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("""

                ===== МЕНЮ =====
                1. Добавить студента вручную
                2. Импортировать список студентов из файла
                3. Показать список студентов
                4. Отфильтровать студентов по среднему баллу (Stream API)
                5. Вывести описания студентов (Stream API, map)
                6. Отсортировать студентов (Stream API)
                7. Отсортировать студентов (выбор алгоритма, поля и направления)
                8. Экспортировать список студентов в файл
                0. Выход
                """);
        System.out.print("Выберите пункт меню: ");
    }

    private static int readCommand() {
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: необходимо ввести число.");
            return -1;
        }
    }

    private static void addStudentManually() {
        try {
            System.out.print("Введите имя студента: ");
            String name = scanner.nextLine().trim();

            System.out.print("Введите средний балл (0.0 - 5.0): ");
            double score = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Введите номер зачетной книжки: ");
            String recordBook = scanner.nextLine().trim();

            StudentValidator.validate(name, score, recordBook);

            students.add(new Student(name, score, recordBook));
            System.out.println("Студент успешно добавлен.");
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: средний балл должен быть числом.");
        } catch (InvalidStudentDataException e) {
            System.out.println("Ошибка валидации: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Непредвиденная ошибка: " + e.getMessage());
        }
    }

    private static void loadStudentsFromFile() {
        System.out.print("Введите путь к файлу (по-умолчанию data/students.txt): ");
        String path = scanner.nextLine().trim();
        CustomArrayList<Student> loaded = fileService.loadStudentsFromFile(path);
        for (Student s : loaded) {
            students.add(s);
        }
    }

    private static void showAllStudents() {
        if (students.isEmpty()) {
            System.out.println("Список студентов пуст.");
            return;
        }
        System.out.println("Текущий список студентов:");
        students.stream().forEach(System.out::println);
    }

    private static void filterStudents() {
        if (students.isEmpty()) {
            System.out.println("Список студентов пуст.");
            return;
        }
        try {
            System.out.print("Введите минимальный средний балл для фильтрации: ");
            double minScore = Double.parseDouble(scanner.nextLine().trim());
            List<Student> filtered = studentService.filterByMinScore(students, minScore);
            if (filtered.isEmpty()) {
                System.out.println("Студентов, удовлетворяющих условию, не найдено.");
            } else {
                System.out.println("Результат фильтрации:");
                filtered.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: необходимо ввести число.");
        }
    }

    private static void mapStudents() {
        if (students.isEmpty()) {
            System.out.println("Список студентов пуст.");
            return;
        }
        List<String> descriptions = studentService.mapToDescription(students);
        System.out.println("Описание студентов:");
        descriptions.forEach(System.out::println);
    }

    private static void sortStudentsViaStream() {
        if (students.isEmpty()) {
            System.out.println("Список студентов пуст.");
            return;
        }
        List<Student> sorted = studentService.sortByScoreUsingStream(students);
        System.out.println("Результат сортировки (Stream API) по среднему баллу (по возрастанию):");
        sorted.forEach(System.out::println);
    }

    private static void sortStudentsCustomAlgorithm() {
        if (students.isEmpty()) {
            System.out.println("Список студентов пуст.");
            return;
        }

        System.out.println("Выберите поле для сортировки: 1 - Имя, 2 - Средний балл, 3 - Номер зачетки");
        String fieldChoice = scanner.nextLine().trim();
        String field = switch (fieldChoice) {
            case "1" -> "name";
            case "2" -> "score";
            case "3" -> "record";
            default -> null;
        };
        if (field == null) {
            System.out.println("Некорректный выбор поля.");
            return;
        }

        System.out.println("Выберите направление сортировки: 1 - По возрастанию, 2 - По убыванию");
        boolean ascending = !"2".equals(scanner.nextLine().trim());

        System.out.println("Выберите алгоритм сортировки: 1 - Пузырьком, 2 - Быстрая сортировка");
        String algoChoice = scanner.nextLine().trim();
        SortAlgorithm<Student> algorithm = "2".equals(algoChoice)
                ? new QuickSortAlgorithm<>()
                : new BubbleSortAlgorithm<>();

        Comparator<Student> comparator = StudentService.comparatorByField(field, ascending);
        studentService.sort(students, algorithm, comparator);

        System.out.println("Сортировка выполнена алгоритмом: " + algorithm.getName());
        showAllStudents();
    }

    private static void saveStudentsToFile() {
        if (students.isEmpty()) {
            System.out.println("Список студентов пуст, сохранять нечего.");
            return;
        }
        System.out.print("Введите путь для сохранения файла: ");
        String path = scanner.nextLine().trim();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (Student s : students) {
                writer.write(String.format("%s,%.2f,%s",
                        s.getName(), s.getAverageScore(), s.getRecordBookNumber()));
                writer.newLine();
            }
            System.out.println("Данные успешно сохранены в файл: " + path);
        } catch (IOException e) {
            System.out.println("Ошибка записи в файл: " + e.getMessage());
        }
    }
}