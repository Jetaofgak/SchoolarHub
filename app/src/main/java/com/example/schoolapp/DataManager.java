package com.example.schoolapp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataManager {
    private static DataManager instance;
    private List<Subject> subjects;
    private List<Student> students;
    private List<Teacher> teachers;
    private List<GroupClass> groups;

    private DataManager() {
        subjects = new ArrayList<>();
        students = new ArrayList<>();

        Subject mathSubject = new Subject("Mathematics");
        Subject scienceSubject = new Subject("Physics");
        Subject chemistrySubject = new Subject("Chemistry");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.MAY, 15);
        Homework mathHomework1 = new Homework(calendar.getTime(), "Math Homework 1");
        calendar.set(2025, Calendar.MAY, 23);
        Homework mathHomework2 = new Homework(calendar.getTime(), "Math 2");
        calendar.set(2025, Calendar.MAY, 8);
        Homework mathHomework3 = new Homework(calendar.getTime(), "Math Quiz 1");
        calendar.set(2025, Calendar.MAY, 16);
        Homework mathHomework4 = new Homework(calendar.getTime(), "Math Assignment 1");
        calendar.set(2025, Calendar.MAY, 20);
        Homework mathHomework5 = new Homework(calendar.getTime(), "Math Problem Set");
        calendar.set(2025, Calendar.MAY, 30);
        Homework mathHomework6 = new Homework(calendar.getTime(), "Math Review");

        mathSubject.AddHomework(mathHomework1);
        mathSubject.AddHomework(mathHomework2);
        mathSubject.AddHomework(mathHomework3);
        mathSubject.AddHomework(mathHomework4);
        mathSubject.AddHomework(mathHomework5);
        mathSubject.AddHomework(mathHomework6);

        calendar.set(2025, Calendar.MAY, 15);
        Homework scienceHomework1 = new Homework(calendar.getTime(), "Physics 1");
        Homework scienceHomework2 = new Homework(calendar.getTime(), "Physics 2");
        calendar.set(2025, Calendar.MAY, 9);
        Homework scienceHomework3 = new Homework(calendar.getTime(), "Physics Lab 1");
        calendar.set(2025, Calendar.MAY, 10);
        Homework scienceHomework4 = new Homework(calendar.getTime(), "Physics Quiz");
        calendar.set(2025, Calendar.JUNE, 1);
        Homework scienceHomework5 = new Homework(calendar.getTime(), "Physics Assignment");
        calendar.set(2025, Calendar.JUNE, 5);
        Homework scienceHomework6 = new Homework(calendar.getTime(), "Physics Review");

        scienceSubject.AddHomework(scienceHomework1);
        scienceSubject.AddHomework(scienceHomework2);
        scienceSubject.AddHomework(scienceHomework3);
        scienceSubject.AddHomework(scienceHomework4);
        scienceSubject.AddHomework(scienceHomework5);
        scienceSubject.AddHomework(scienceHomework6);

        calendar.set(2025, Calendar.MAY, 25);
        Homework chemistryHomework1 = new Homework(calendar.getTime(), "Chemistry Lab Report");

        chemistrySubject.AddHomework(chemistryHomework1);

        subjects.add(mathSubject);
        subjects.add(scienceSubject);
        subjects.add(chemistrySubject);

        Grade mathGrade1 = new Grade(90, "Test 1");
        Grade mathGrade2 = new Grade(85, "Test 2");
        Grade scienceGrade1 = new Grade(88, "Physics Midterm");
        Grade scienceGrade2 = new Grade(92, "Physics Final");

        mathSubject.AddGrade(mathGrade1);
        mathSubject.AddGrade(mathGrade2);
        scienceSubject.AddGrade(scienceGrade1);
        scienceSubject.AddGrade(scienceGrade2);

        teachers = new ArrayList<>();
        teachers.add(new Teacher("Alice Johnson", 1, 35));
        teachers.add(new Teacher("Bob Smith", 2, 42));
        teachers.add(new Teacher("Catherine Lee", 3, 29));
        teachers.add(new Teacher("David Brown", 4, 38));
        teachers.add(new Teacher("Emily Davis", 5, 33));
        teachers.add(new Teacher("Frank Wilson", 6, 45));
        teachers.add(new Teacher("Grace Miller", 7, 31));
        teachers.add(new Teacher("Henry Moore", 8, 50));
        teachers.add(new Teacher("Isabella Taylor", 9, 28));
        teachers.add(new Teacher("Jack Anderson", 10, 41));
        teachers.add(new Teacher("Karen Thomas", 11, 36));
        teachers.add(new Teacher("Liam Jackson", 12, 39));
        teachers.add(new Teacher("Mia White", 13, 34));
        teachers.add(new Teacher("Noah Harris", 14, 46));
        teachers.add(new Teacher("Olivia Martin", 15, 30));
        teachers.add(new Teacher("Paul Thompson", 16, 44));
        teachers.add(new Teacher("Quinn Garcia", 17, 32));
        teachers.add(new Teacher("Rachel Martinez", 18, 37));
        teachers.add(new Teacher("Samuel Robinson", 19, 40));
        teachers.add(new Teacher("Tina Clark", 20, 27));

        populateStudents();
    }

    private void populateStudents() {
        groups = new ArrayList<>();

        GroupClass g1 = new GroupClass("A", 1);
        GroupClass g2 = new GroupClass("B", 2);
        GroupClass g3 = new GroupClass("C", 2);
        GroupClass g4 = new GroupClass("D", 3);
        GroupClass g5 = new GroupClass("E", 4);

        Student s1 = new Student(1, "Ali", 15, g1);
        Student s2 = new Student(2, "Zara", 16, g1);
        Student s3 = new Student(3, "Mounir", 17, g2);
        Student s4 = new Student(4, "Leila", 14, g2);
        Student s5 = new Student(5, "Sami", 15, g3);
        Student s6 = new Student(6, "Layla", 16, g3);
        Student s7 = new Student(7, "Nada", 17, g3);
        Student s8 = new Student(8, "Rania", 16, g3);
        Student s9 = new Student(9, "Dina", 14, g3);
        Student s10 = new Student(10, "Hassan", 17, g4);
        Student s11 = new Student(11, "Walid", 15, g4);
        Student s12 = new Student(12, "Fatima", 16, g4);
        Student s13 = new Student(13, "Tariq", 15, g4);
        Student s14 = new Student(14, "Salma", 15, g4);
        Student s15 = new Student(15, "Fouad", 14, g5);
        Student s16 = new Student(16, "Khalid", 14, g5);
        Student s17 = new Student(17, "Maya", 17, g5);
        Student s18 = new Student(18, "Imane", 17, g5);
        Student s19 = new Student(19, "Ibrahim", 17, g5);
        Student s20 = new Student(20, "Nora", 14, g1);
        Student s21 = new Student(21, "Amina", 17, g1);
        Student s22 = new Student(22, "Rami", 14, g2);
        Student s23 = new Student(23, "Omar", 14, g2);
        Student s24 = new Student(24, "Youssef", 17, g2);

        g1.students.addAll(List.of(s1, s2, s20, s21));
        g2.students.addAll(List.of(s3, s4, s22, s23, s24));
        g3.students.addAll(List.of(s5, s6, s7, s8, s9));
        g4.students.addAll(List.of(s10, s11, s12, s13, s14));
        g5.students.addAll(List.of(s15, s16, s17, s18, s19));

        students.addAll(List.of(
                s1, s2, s3, s4, s5, s6, s7, s8, s9,
                s10, s11, s12, s13, s14, s15, s16, s17,
                s18, s19, s20, s21, s22, s23, s24
        ));

        groups.addAll(List.of(g1, g2, g3, g4, g5));
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public List<GroupClass> getGroups() {
        return groups;
    }

    public void addGradeToSubject(String subjectName, Grade grade) {
        for (Subject subject : subjects) {
            if (subject.getName().equals(subjectName)) {
                subject.AddGrade(grade);
                break;
            }
        }
    }

    public List<Calendar> getUniqueHomeworkDates() {
        Set<Long> uniqueDates = new HashSet<>();
        List<Calendar> dates = new ArrayList<>();

        for (Subject subject : subjects) {
            for (Homework homework : subject.getHomeworks()) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(homework.getDueDate());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                long dateInMillis = calendar.getTimeInMillis();
                if (!uniqueDates.contains(dateInMillis) && uniqueDates.size() < 7) {
                    uniqueDates.add(dateInMillis);
                    dates.add((Calendar) calendar.clone());
                }
            }
        }

        // Sort dates in chronological order
        Collections.sort(dates, new Comparator<Calendar>() {
            @Override
            public int compare(Calendar c1, Calendar c2) {
                return Long.compare(c1.getTimeInMillis(), c2.getTimeInMillis());
            }
        });

        return dates;
    }
}