package com.example.schoolapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Subject {
    String name;
    boolean emergency;
    List<Homework> homeworks;
    List<Grade> grades;

    public Subject() {
        this.emergency = false;
        this.homeworks = new ArrayList<>();
        this.grades = new ArrayList<>();
    }

    public Subject(String n) {
        this.name = n;
        this.emergency = false;
        this.homeworks = new ArrayList<>();
        this.grades = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public boolean getEmergency() {
        return emergency;
    }

    public void CheckEmergency() {
        Calendar now = Calendar.getInstance();
        // Normalize today's date to midnight
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        long todayMillis = now.getTimeInMillis();

        // Set end of emergency period (3 days from today)
        Calendar endDate = (Calendar) now.clone();
        endDate.add(Calendar.DAY_OF_MONTH, 3);
        long endMillis = endDate.getTimeInMillis();

        emergency = false; // Reset emergency flag
        for (Homework homework : homeworks) {
            Calendar homeworkDate = Calendar.getInstance();
            homeworkDate.setTime(homework.getDueDate());
            // Normalize homework date to midnight
            homeworkDate.set(Calendar.HOUR_OF_DAY, 0);
            homeworkDate.set(Calendar.MINUTE, 0);
            homeworkDate.set(Calendar.SECOND, 0);
            homeworkDate.set(Calendar.MILLISECOND, 0);
            long homeworkMillis = homeworkDate.getTimeInMillis();

            if (homeworkMillis >= todayMillis && homeworkMillis <= endMillis) {
                emergency = true;
                break; // No need to check further
            }
        }
    }

    public void AddHomework(Homework hw) {
        homeworks.add(hw);
    }

    public List<Homework> getHomeworks() {
        return homeworks;
    }

    public void AddGrade(Grade grade) {
        grades.add(grade);
    }

    public List<Grade> getGrades() {
        return grades; // Added: Return the grades list
    }
}