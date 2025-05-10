// Student.java
package com.example.schoolapp.model;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private Group group;
    private List<Grade> grades = new ArrayList<>();  // Many Grades association

    public Student(long id, String email, String password,
                   String firstname, String lastname,int age, Group group) {
        super(id, email, password, firstname, lastname,age);
        this.group = group;
        group.addStudent(this);
    }

    // Existing group methods
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        if (this.group != null) {
            this.group.removeStudent(this);
        }
        this.group = group;
        if (group != null) {  // Added null check
            group.addStudent(this);
        }
    }

    // Grade-related methods
    public List<Grade> getGrades() {
        return grades;
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
        grade.setStudent(this);  // Maintain bidirectional relationship
    }

    public void removeGrade(Grade grade) {
        grades.remove(grade);
        grade.setStudent(null);
    }
}
