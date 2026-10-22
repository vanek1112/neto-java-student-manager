package org.example.model;

import java.util.Objects;

public class Student {

    private String name;
    private double averageScore;
    private String recordBookNumber;

    public Student(String name, double averageScore, String recordBookNumber) {
        this.name = name;
        this.averageScore = averageScore;
        this.recordBookNumber = recordBookNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public String getRecordBookNumber() {
        return recordBookNumber;
    }

    public void setRecordBookNumber(String recordBookNumber) {
        this.recordBookNumber = recordBookNumber;
    }

    @Override
    public String toString() {
        return String.format("Студент{имя='%s', средний балл=%.2f, зачетка='%s'}",
                name, averageScore, recordBookNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return Double.compare(student.averageScore, averageScore) == 0
                && Objects.equals(name, student.name)
                && Objects.equals(recordBookNumber, student.recordBookNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, averageScore, recordBookNumber);
    }
}