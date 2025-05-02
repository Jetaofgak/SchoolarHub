package com.example.schoolapp;

import java.util.ArrayList;
import java.util.List;
import android.util.Pair;

public class GroupClass {
    String name;
    List<Student> students;
    int lvl;

    public GroupClass(String name, int lvl,List<Student> students) {
        this.name = name;
        this.students = students;
        this.lvl = lvl;
    }

    public GroupClass(String name, int lvl) {
        this.name = name;
        this.lvl = lvl;
        students = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getLvl() {
        return lvl;
    }

    public Pair<String,Integer> getRightGroupForStudent(Student student)
    {
        for(Student StudentChecker: students)
        {
            if(StudentChecker == student)
            {
                return new Pair<>(getName(),getLvl());
            }
        }
        return new Pair<>("NoFound",0);
    }
}
