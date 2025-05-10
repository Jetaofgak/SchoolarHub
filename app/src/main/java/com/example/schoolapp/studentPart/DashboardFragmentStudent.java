package com.example.schoolapp.studentPart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.schoolapp.R;
import com.example.schoolapp.model.*;
import com.example.schoolapp.service.DataManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashboardFragmentStudent extends Fragment {
    private LinearLayout dateContainer;
    private TextView numberOfAssignments;
    private TextView numberOfAssignmentsThisWeek;
    private TextView lastGradeSubject;
    private TextView lastGradeSubjectGrade;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_student, container, false);

        // Initialize views
        dateContainer = view.findViewById(R.id.dateContainer);
        numberOfAssignments = view.findViewById(R.id.numberOfAssignments);
        numberOfAssignmentsThisWeek = view.findViewById(R.id.numberOfAssignmentsThisWeek);
        lastGradeSubject = view.findViewById(R.id.lastGradeSubject);
        lastGradeSubjectGrade = view.findViewById(R.id.lastGradeSubjectGrade);

        // Get DataManager instance and current student
        DataManager dataManager = DataManager.getInstance();
        Student currentStudent = getCurrentStudent(dataManager);

        // Populate dashboard data
        if (currentStudent != null) {
            populateDashboardData(dataManager, currentStudent);
        }

        return view;
    }

    private Student getCurrentStudent(DataManager dataManager) {
        // Placeholder for authentication logic; returns first student for now
        List<Student> students = dataManager.getStudents();
        return students.isEmpty() ? null : students.get(0);
    }

    private void populateDashboardData(DataManager dataManager, Student student) {
        Group studentGroup = student.getGroup();
        LevelOfGroups studentLevel = studentGroup != null ? studentGroup.getLevel() : null;
        int levelNumber = studentLevel != null ? studentLevel.getLvl() : -1;

        List<Homework> levelHomework = dataManager.getHomeworkForLevel(levelNumber);
        List<Grade> studentGrades = dataManager.getGradesForStudent(student.getId());

        updateAssignmentCount(levelHomework);
        updateWeeklyAssignments(levelHomework);
        updateLastGrade(studentGrades);
        updateUpcomingDates(dataManager, levelNumber);
    }

    private void updateAssignmentCount(List<Homework> homeworkList) {
        int totalAssignments = homeworkList.size();
        numberOfAssignments.setText(String.format(Locale.getDefault(), "%d Assignment%s to do",
                totalAssignments, totalAssignments != 1 ? "s" : ""));
    }

    private void updateWeeklyAssignments(List<Homework> homeworkList) {
        int assignmentsThisWeek = getAssignmentsThisWeek(homeworkList);
        numberOfAssignmentsThisWeek.setText(String.format(Locale.getDefault(), "%d Assignment%s this week",
                assignmentsThisWeek, assignmentsThisWeek != 1 ? "s" : ""));
    }

    private int getAssignmentsThisWeek(List<Homework> homeworkList) {
        Calendar now = Calendar.getInstance();
        Calendar startOfWeek = (Calendar) now.clone();
        startOfWeek.set(Calendar.DAY_OF_WEEK, startOfWeek.getFirstDayOfWeek());
        setTimeToStartOfDay(startOfWeek);

        Calendar endOfWeek = (Calendar) startOfWeek.clone();
        endOfWeek.add(Calendar.DAY_OF_YEAR, 6);
        setTimeToEndOfDay(endOfWeek);

        return (int) homeworkList.stream()
                .filter(hw -> {
                    Calendar dueDate = Calendar.getInstance();
                    dueDate.setTime(hw.getDueDate());
                    return !dueDate.before(startOfWeek) && !dueDate.after(endOfWeek);
                })
                .count();
    }

    private void updateLastGrade(List<Grade> grades) {
        if (grades.isEmpty()) {
            lastGradeSubject.setText("No grades yet");
            lastGradeSubjectGrade.setText("");
            return;
        }

        Grade bestGrade = Collections.max(grades, (g1, g2) -> Double.compare(g1.getNote(), g2.getNote()));
        lastGradeSubject.setText(String.format(Locale.getDefault(), "Best grade: %s",
                bestGrade.getSubject().getTitle()));
        lastGradeSubjectGrade.setText(String.format(Locale.getDefault(), "%s: %.1f",
                bestGrade.getDescription(), bestGrade.getNote()));
    }

    private void updateUpcomingDates(DataManager dataManager, int levelNumber) {
        List<Date> upcomingDates = dataManager.getUpcomingDatesForLevel(levelNumber, 7);
        dateContainer.removeAllViews();

        if (upcomingDates == null || upcomingDates.isEmpty()) {
            TextView noDates = new TextView(requireContext());
            noDates.setText("No upcoming deadlines");
            dateContainer.addView(noDates);
            return;
        }

        int maxDates = Math.min(upcomingDates.size(), 7);
        float weight = 1.0f / maxDates;

        for (int i = 0; i < maxDates; i++) {
            TextView dateView = new TextView(requireContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, weight);
            params.setMargins(4, 0, 4, 0);

            dateView.setLayoutParams(params);
            dateView.setText(dateFormat.format(upcomingDates.get(i)));
            dateView.setPadding(16, 8, 16, 8);
            dateView.setBackgroundResource(R.drawable.date_background);
            dateView.setTextSize(16);
            dateView.setGravity(android.view.Gravity.CENTER);

            dateContainer.addView(dateView);
        }
    }


    private void setTimeToStartOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private void setTimeToEndOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }
}