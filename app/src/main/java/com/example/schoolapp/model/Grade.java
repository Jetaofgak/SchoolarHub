// Grade.java
package com.example.schoolapp.model;

public class Grade {
    private int id;
    private String description;
    private double note;
    private Student student;
    private Subject subject;  // One Subject association

    public Grade(int id, String description, double note, Subject subject) {
        this.id = id;
        this.description = description;
        this.note = note;
        this.subject = subject;
    }

    // Existing getters and setters
    public int getId() { return id; }
    public String getDescription() { return description; }
    public double getNote() { return note; }
    public Student getStudent() { return student; }
    public Subject getSubject() { return subject; }

    // Student relationship
    public void setStudent(Student student) {
        if (this.student != null) {
            this.student.getGrades().remove(this);
        }
        this.student = student;
        if (student != null && !student.getGrades().contains(this)) {
            student.getGrades().add(this);
        }
    }

    // Subject relationship
    public void setSubject(Subject subject) {
        if (this.subject != null) {
            this.subject.getGrades().remove(this);
        }
        this.subject = subject;
        if (subject != null && !subject.getGrades().contains(this)) {
            subject.getGrades().add(this);
        }
    }
}