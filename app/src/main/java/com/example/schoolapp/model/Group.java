// Group.java
package com.example.schoolapp.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private int id;
    private List<Subject> subjects = new ArrayList<>();
    private int numeric;
    private List<Teacher> teachers = new ArrayList<>();  // Many Teachers association
    private String time;
    private LevelOfGroups level;  // One Level association
    private List<Student> students = new ArrayList<>();  // Many Students association

    public Group(int id, int numeric, String time, LevelOfGroups level) {
        this.id = id;
        this.numeric = numeric;
        this.time = time;
        this.level = level;
    }

    // Getters and setters
    public int getId() { return id; }
    public int getNumeric() { return numeric; }
    public String getTime() { return time; }
    public LevelOfGroups getLevel() { return level; }  // Changed return type
    public List<Student> getStudents() { return students; }

    public void setLevel(LevelOfGroups level) { this.level = level; }
    public void addStudent(Student student) { this.students.add(student); }
    public void removeStudent(Student student) { this.students.remove(student); }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }
}
