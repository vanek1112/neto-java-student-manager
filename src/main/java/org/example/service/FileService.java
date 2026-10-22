package org.example.service;

import org.example.collection.CustomArrayList;
import org.example.exception.InvalidStudentDataException;
import org.example.model.Student;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileService {

    private static final String DELIMITER = ",";

    public CustomArrayList<Student> loadStudentsFromFile(String filePath) {
        CustomArrayList<Student> students = new CustomArrayList<>();
        Path path = Path.of(filePath);

        if (!Files.exists(path)) {
            System.out.println("Ошибка: файл не найден по пути: " + filePath);
            return students;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }
                try {
                    students.add(parseLine(line));
                } catch (InvalidStudentDataException e) {
                    System.out.printf("Строка %d пропущена: %s%n", lineNumber, e.getMessage());
                }
            }
            System.out.println("Загрузка завершена. Успешно добавлено студентов: " + students.size());
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }

        return students;
    }

    private Student parseLine(String line) throws InvalidStudentDataException {
        String[] parts = line.split(DELIMITER);
        if (parts.length != 3) {
            throw new InvalidStudentDataException("Неверный формат строки: \"" + line + "\"");
        }

        String name = parts[0].trim();
        double averageScore;
        try {
            averageScore = Double.parseDouble(parts[1].trim());
        } catch (NumberFormatException e) {
            throw new InvalidStudentDataException("Некорректный средний балл: \"" + parts[1].trim() + "\"");
        }
        String recordBook = parts[2].trim();

        StudentValidator.validate(name, averageScore, recordBook);

        return new Student(name, averageScore, recordBook);
    }
}