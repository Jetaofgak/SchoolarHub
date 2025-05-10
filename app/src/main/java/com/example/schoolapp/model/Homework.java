package com.example.schoolapp.model;

import java.util.Date;

public class Homework {
    private int id;
    private String description;
    private Date deadline;
    private Subject subject;  // Many-to-one with Subject

    public Homework(int id, String description, Date deadline) {
        this.id = id;
        this.description = description;
        this.deadline = new Date(deadline.getTime()); // Defensive copy
    }

    // Subject relationship
    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        if (this.subject != null) {
            this.subject.getHomeworks().remove(this);
        }
        this.subject = subject;
        if (subject != null && !subject.getHomeworks().contains(this)) {
            subject.getHomeworks().add(this);
        }
    }

    public String getDescription() {
        return description;
    }

    public Date getDueDate() {
        return new Date(deadline.getTime()); // Defensive copy
    }

    public int getId() {
        return id;
    }
}