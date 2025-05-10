package com.example.schoolapp.service;

import com.example.schoolapp.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class DataManager {
    private static DataManager instance;
    private List<Subject> subjects;
    private List<Student> students;
    private List<Teacher> teachers;
    private List<Group> groups;
    private List<LevelOfGroups> levels;
    private List<Admin> admins;

    private DataManager() {
        subjects = new ArrayList<>();
        students = new ArrayList<>();
        teachers = new ArrayList<>();
        groups = new ArrayList<>();
        levels = new ArrayList<>();
        admins = new ArrayList<>();

        initializeData();
    }

    private void initializeData() {
        // 1. Initialize Levels (simplified)
        LevelOfGroups level1 = new LevelOfGroups(1, "First Year/Beginner");
        LevelOfGroups level2 = new LevelOfGroups(2, "Second Year/Intermediate");
        levels.addAll(Arrays.asList(level1, level2));

        // 2. Initialize Groups with level references
        Group groupA = new Group(1, 101, "Mon/Wed 9-11", level1);
        Group groupB = new Group(2, 102, "Tue/Thu 10-12", level2);
        groups.addAll(Arrays.asList(groupA, groupB));

        // 3. Initialize Subjects
        Subject math = new Subject(1, "Mathematics");
        Subject physics = new Subject(2, "Physics");
        subjects.addAll(Arrays.asList(math, physics));

        // Add groups to the subject Mathematics
        math.getGroups().add(groupA);  // Add groupA to Mathematics
        math.getGroups().add(groupB);  // Add groupB to Mathematics

        // 4. Initialize Teachers with subjects and groups
        Teacher alice = new Teacher(1, "t1@school.com", "pass123", "Alice", "Johnson", 38);
        Teacher bob = new Teacher(2, "t2@school.com", "pass123", "Bob", "Smith", 50);

        // Assign subjects and groups to teachers
        alice.getSubjects().add(math);
        bob.getSubjects().add(physics);
        alice.getGroups().add(groupA);
        bob.getGroups().add(groupB);

        teachers.add(alice);
        teachers.add(bob);

        // 5. Initialize Students
        students.add(new Student(1, "s1@school.com", "pass123", "Ali", "Khan", 18, groupA));
        students.add(new Student(2, "s2@school.com", "pass123", "Zara", "Ahmed", 19, groupB));

        // 6. Initialize Admins
        admins.add(new Admin(1, "admin@school.com", "admin123", "Main", "Admin", "City High School", 40));
        // Add another student in level 1 (groupA)
        Student student2 = new Student(3, "s3@school.com", "pass123", "Emma", "Watson", 20, groupA);
        students.add(student2);

        // 7. Add grades for both students in level 1
        Grade mathGrade = new Grade(1, "Midterm Exam", 85.0, math);
        mathGrade.setStudent(students.get(0));

        Grade mathGrade2 = new Grade(2, "Quiz 1", 90.0, math);
        mathGrade2.setStudent(student2);  // Student in level 1

        Grade physicsGrade = new Grade(3, "Lab Report", 88.0, physics);
        physicsGrade.setStudent(students.get(0));  // Student in level 1

        // 8. Add Homework for next 7 days
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(new Date()); // Set to current date/time
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        String[] mathTopics = {"Algebra", "Calculus", "Geometry", "Trigonometry"};
        String[] physicsTopics = {"Mechanics", "Thermodynamics", "Electromagnetism", "Optics"};

        for (int i = 1; i <= 7; i++) {
            cal.add(Calendar.DATE, 1); // Move to next day

            Subject subject = (i % 2 == 0) ? physics : math; // Alternate subjects
            String topic = (subject == math)
                    ? mathTopics[i % mathTopics.length]
                    : physicsTopics[i % physicsTopics.length];

            Homework hw = new Homework(
                    100 + i, // Unique ID
                    topic + " Assignment " + i,
                    cal.getTime()
            );
            subject.addHomework(hw);

            // Ensure at least one grade exists per subject
            if (subject.getGrades().isEmpty()) {
                Grade grade = new Grade(
                        200 + i,
                        "Grade for " + topic,
                        75 + (i * 3), // Grades from 78-96
                        subject
                );
                grade.setStudent(students.get(i % students.size()));
            }
        }
    }

    public static synchronized DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }
    // Level-specific methods
    public LevelOfGroups getLevelByNumber(int levelNumber) {
        for (LevelOfGroups level : levels) {
            if (level.getLvl() == levelNumber) {
                return level;
            }
        }
        return null;
    }

    public List<Group> getGroupsByLevel(int levelNumber) {
        List<Group> result = new ArrayList<>();
        for (Group group : groups) {
            if (group.getLevel().getLvl() == levelNumber) {
                result.add(group);
            }
        }
        return result;
    }

    // Core getters (return unmodifiable lists)
    public List<Student> getStudents() { return students; }
    public List<Subject> getSubjects() { return subjects; }
    public List<Teacher> getTeachers() { return teachers; }
    public List<Group> getGroups() { return groups; }
    public List<LevelOfGroups> getLevels() { return levels; }
    public List<Admin> getAdmins() { return admins; }

    // Relationship management
    public void addStudentToGroup(Student student, Group group) {
        student.setGroup(group);
        if (!students.contains(student)) {
            students.add(student);
        }
    }

    // Utility methods
    public List<Homework> getUpcomingHomework(int days) {
        List<Homework> upcoming = new ArrayList<>();
        long now = System.currentTimeMillis();
        long cutoff = now + days * 24 * 60 * 60 * 1000L;

        for (Subject subject : subjects) {
            for (Homework hw : subject.getHomeworks()) {
                if (hw.getDueDate().getTime() <= cutoff) {
                    upcoming.add(hw);
                }
            }
        }
        return upcoming;
    }

    // --- Added Methods ---

    // Get homework for a specific level
    public List<Homework> getHomeworkForLevel(int levelNumber) {
        List<Homework> homeworkForLevel = new ArrayList<>();
        List<Group> groupsInLevel = getGroupsByLevel(levelNumber);

        for (Subject subject : subjects) {
            for (Homework homework : subject.getHomeworks()) {
                // Check if any student in this level has grades in the homework's subject
                boolean hasStudentInLevel = students.stream()
                        .anyMatch(student ->
                                groupsInLevel.contains(student.getGroup()) &&
                                        student.getGrades().stream()
                                                .anyMatch(grade -> grade.getSubject().equals(subject))
                        );

                if (hasStudentInLevel) {
                    homeworkForLevel.add(homework);
                }
            }
        }
        return homeworkForLevel;
    }
    // Get grades for a specific student
    public List<Grade> getGradesForStudent(long studentId) {
        for (Student student : students) {
            if (student.getId() == studentId) {
                return student.getGrades();
            }
        }
        return new ArrayList<>(); // Return empty list if student not found
    }

    // Get upcoming dates for a specific level (within the next 'days' days)
    public List<Date> getUpcomingDatesForLevel(int levelNumber, int days) {
        List<Homework> homeworkList = getHomeworkForLevel(levelNumber);
        List<Date> upcomingDates = new ArrayList<>();
        long now = System.currentTimeMillis();
        long cutoff = now + (days * 86_400_000L);  // days in milliseconds

        for (Homework homework : homeworkList) {
            long dueTime = homework.getDueDate().getTime();
            if (dueTime >= now && dueTime <= cutoff) {  // Include dates up to AND ON the cutoff day
                upcomingDates.add(homework.getDueDate());
            }
        }
        Collections.sort(upcomingDates);
        return upcomingDates;
    }
}