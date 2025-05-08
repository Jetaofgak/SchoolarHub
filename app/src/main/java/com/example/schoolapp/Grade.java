package com.example.schoolapp;

public class Grade {

    int note;
    String description;

    public Grade(int n,String desc)
    {
        note =n;
        description = desc;
    }
    public int getScore() {
        return note;
    }

    public String getDescription() {
        return description;
    }
}
