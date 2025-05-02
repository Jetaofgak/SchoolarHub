package com.example.schoolapp;

import androidx.constraintlayout.widget.Group;

public class Student {
    int id;
    GroupClass group;
    String name;
    int Age;


    public Student(int id, String name, int age,GroupClass g) {
        this.id = id;
        group = g;
        this.name = name;
        Age = age;
    }
}
