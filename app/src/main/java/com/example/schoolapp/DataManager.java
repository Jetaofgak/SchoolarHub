package com.example.schoolapp;



import androidx.constraintlayout.widget.Group;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataManager {
    private static DataManager instance;
    private List<Subject> subjects;
    private List<Teacher> teachers;
    private List<Student> students;
    private  List<GroupClass> groups;


    // Private constructor to prevent instantiation
    private DataManager() {
        subjects = new ArrayList<>();
        Subject mathSubject = new Subject("Mathematics");
        Subject scienceSubject = new Subject("Physics");

        // Add homework to subjects
        Homework mathHomework1 = new Homework(new Date(2025, 4, 15), "Math Homework 1");
        Homework mathHomework2 = new Homework(new Date(2025, 4, 23), "Math 2");
        Homework scienceHomework1 = new Homework(new Date(2025, 4, 15), "Physics 1");
        Homework scienceHomework2 = new Homework(new Date(2025, 4, 15), "Physics 2");

        mathSubject.AddHomework(mathHomework1);
        mathSubject.AddHomework(mathHomework2);
        scienceSubject.AddHomework(scienceHomework1);
        scienceSubject.AddHomework(scienceHomework2);

        // Add subjects to the subjects list
        subjects.add(mathSubject);
        subjects.add(scienceSubject);

        // Add some grades to the subjects
        Grade mathGrade1 = new Grade(90, "Test 1");
        Grade mathGrade2 = new Grade(85, "Test 2");
        Grade scienceGrade1 = new Grade(88, "Physics Midterm");
        Grade scienceGrade2 = new Grade(92, "Physics Final");

        // Add grades to respective subjects
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
        students = new ArrayList<>();
        groups = new ArrayList<>();
        // Create the groups
        GroupClass g1 = new GroupClass("Group A", 1);
        GroupClass g2 = new GroupClass("Group B", 2);
        GroupClass g3 = new GroupClass("Group C", 2);
        GroupClass g4 = new GroupClass("Group D", 3);
        GroupClass g5 = new GroupClass("Group E", 4);


        // Create students and assign them to groups
        Student s1 = new Student(1,  "Ali", 15);
        Student s2 = new Student(2,  "Zara", 16);
        Student s3 = new Student(3,  "Mounir", 17);
        Student s4 = new Student(4,  "Leila", 14);
        Student s5 = new Student(5, "Sami", 15);
        Student s6 = new Student(6, "Layla", 16);
        Student s7 = new Student(7, "Nada", 17);
        Student s8 = new Student(8, "Rania", 16);
        Student s9 = new Student(9, "Dina", 14);

        Student s10 = new Student(10, "Hassan", 17);
        Student s11 = new Student(11, "Walid", 15);
        Student s12 = new Student(12, "Fatima", 16);
        Student s13 = new Student(13, "Tariq", 15);
        Student s14 = new Student(14, "Salma", 15);

        Student s15 = new Student(15, "Fouad", 14);
        Student s16 = new Student(16, "Khalid", 14);
        Student s17 = new Student(17, "Maya", 17);
        Student s18 = new Student(18, "Imane", 17);
        Student s19 = new Student(19, "Ibrahim", 17);

        Student s20 = new Student(20, "Nora", 14);
        Student s21 = new Student(21, "Amina", 17);
        Student s22 = new Student(22, "Rami", 14);
        Student s23 = new Student(23, "Omar", 14);
        Student s24 = new Student(24, "Youssef", 17);


        // Add students to their respective group lists
        g1.students.add(s1);
        g1.students.add(s2);
        g2.students.add(s3);
        g2.students.add(s4);
        g3.students.addAll(List.of(s5, s6, s7, s8, s9));
        g4.students.addAll(List.of(s10, s11, s12, s13, s14));
        g5.students.addAll(List.of(s15, s16, s17, s18, s19));
        g1.students.addAll(List.of(s20, s21));
        g2.students.addAll(List.of(s22, s23, s24));


        // Add students to global student list (if needed)
        students.add(s1);
        students.add(s2);
        students.add(s3);
        students.add(s4);
        students.addAll(List.of(
                s5, s6, s7, s8, s9,
                s10, s11, s12, s13, s14,
                s15, s16, s17, s18, s19,
                s20, s21, s22, s23, s24
        ));
        groups.add(g1);
        groups.add(g2);
        groups.addAll(List.of(g3, g4, g5));


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

    public List<GroupClass> getGroups()
    {
        return groups;
    }

    // Method to add a grade to a specific subject
    public void addGradeToSubject(String subjectName, Grade grade) {
        for (Subject subject : subjects) {
            if (subject.getName().equals(subjectName)) {
                subject.AddGrade(grade);
                break;
            }
        }
    }
}

