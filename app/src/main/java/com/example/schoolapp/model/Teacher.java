package com.example.schoolapp.model;

import java.util.ArrayList;
import java.util.List;
public class Teacher extends User {
    private List<Subject> subjects = new ArrayList<>(); // One-to-many with Subject
    private List<Group> groups = new ArrayList<>(); // One-to-many with Group
    public Teacher(long id, String email, String password, String firstname, String lastname, int age) {
        super(id, email, password, firstname, lastname,age);
    }

    public List<Subject> getSubjects() {
        return subjects;
    }


    public List<Group> getGroups() {
        return groups;
    }
}


