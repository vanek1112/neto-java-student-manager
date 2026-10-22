package org.example.service;

import org.example.exception.InvalidStudentDataException;

public final class StudentValidator {

    private static final double MIN_SCORE = 0.0;
    private static final double MAX_SCORE = 5.0;

    private StudentValidator() {
    }

    public static void validate(String name, double averageScore, String recordBookNumber)
            throws InvalidStudentDataException {

        if (name == null || name.isBlank()) {
            throw new InvalidStudentDataException("Имя студента не может быть пустым.");
        }
        if (averageScore < MIN_SCORE || averageScore > MAX_SCORE) {
            throw new InvalidStudentDataException(
                    String.format("Средний балл должен быть в диапазоне [%.1f; %.1f].", MIN_SCORE, MAX_SCORE));
        }
        if (recordBookNumber == null || recordBookNumber.isBlank()) {
            throw new InvalidStudentDataException("Номер зачетной книжки не может быть пустым.");
        }
        if (!recordBookNumber.matches("[A-Za-zА-Яа-я0-9\\-]+")) {
            throw new InvalidStudentDataException("Номер зачетной книжки содержит недопустимые символы.");
        }
    }
}