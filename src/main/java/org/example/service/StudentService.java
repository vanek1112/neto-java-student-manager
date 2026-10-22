package org.example.service;

import org.example.collection.CustomArrayList;
import org.example.model.Student;
import org.example.sort.SortAlgorithm;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StudentService {

    public List<Student> filterByMinScore(CustomArrayList<Student> students, double minScore) {
        return students.stream()
                .filter(s -> s.getAverageScore() >= minScore)
                .collect(Collectors.toList());
    }

    public List<String> mapToDescription(CustomArrayList<Student> students) {
        return students.stream()
                .map(s -> String.format("%s (зачетка %s) - %s, средний балл: %.2f",
                        s.getName(),
                        s.getRecordBookNumber(),
                        categorize(s.getAverageScore()),
                        s.getAverageScore()))
                .collect(Collectors.toList());
    }

    public List<Student> sortByScoreUsingStream(CustomArrayList<Student> students) {
        return students.stream()
                .sorted(Comparator.comparingDouble(Student::getAverageScore))
                .collect(Collectors.toList());
    }

    public void sort(CustomArrayList<Student> students, SortAlgorithm<Student> algorithm,
                     Comparator<Student> comparator) {
        algorithm.sort(students, comparator);
    }

    public static Comparator<Student> comparatorByField(String field, boolean ascending) {
        Comparator<Student> comparator = switch (field) {
            case "name" -> Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER);
            case "score" -> Comparator.comparingDouble(Student::getAverageScore);
            case "record" -> Comparator.comparing(Student::getRecordBookNumber);
            default -> throw new IllegalArgumentException("Неизвестное поле для сортировки: " + field);
        };
        return ascending ? comparator : comparator.reversed();
    }

    private String categorize(double score) {
        if (score >= 4.5) return "Отличник";
        if (score >= 3.5) return "Хорошист";
        if (score >= 2.5) return "Троечник";
        return "Неуспевающий";
    }
}