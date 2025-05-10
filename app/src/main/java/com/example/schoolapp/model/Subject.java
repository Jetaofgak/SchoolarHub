// Subject.java
package com.example.schoolapp.model;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private int id;
    private String title;
    private List<Group> groups = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();
    private List<Grade> grades = new ArrayList<>();
    private List<Homework> homeworks = new ArrayList<>();  // One-to-many with Homework

    public Subject(int id, String title) {
        this.id = id;
        this.title = title;
    }

    // Existing methods...

    // Homework relationship management
    public List<Homework> getHomeworks() {
        return homeworks;
    }

    public void addHomework(Homework homework) {
        if (!homeworks.contains(homework)) {
            homeworks.add(homework);
            homework.setSubject(this);  // Maintain bidirectional relationship
        }
    }

    public void removeHomework(Homework homework) {
        homeworks.remove(homework);
        homework.setSubject(null);
    }

    public List<Grade> getGrades() {
        return grades;
    }


    public String getTitle() {
        return title;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public int getId() {
        return id;
    }

    public List<Group> getGroups() {
        return groups;
    }
}