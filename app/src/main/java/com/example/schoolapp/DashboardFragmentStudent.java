package com.example.schoolapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_student, container, false);

        // Initialize views
        dateContainer = view.findViewById(R.id.dateContainer);
        numberOfAssignments = view.findViewById(R.id.numberOfAssignments);
        numberOfAssignmentsThisWeek = view.findViewById(R.id.numberOfAssignmentsThisWeek);
        lastGradeSubject = view.findViewById(R.id.lastGradeSubject);
        lastGradeSubjectGrade = view.findViewById(R.id.lastGradeSubjectGrade);

        // Get DataManager instance
        DataManager dataManager = DataManager.getInstance();

        // Populate total assignments
        int totalAssignments = getTotalAssignments(dataManager);
        numberOfAssignments.setText(totalAssignments + " Assignment" + (totalAssignments != 1 ? "s" : "") + " to do");

        // Populate assignments this week
        int assignmentsThisWeek = getAssignmentsThisWeek(dataManager);
        numberOfAssignmentsThisWeek.setText(assignmentsThisWeek + " Assignment" + (assignmentsThisWeek != 1 ? "s" : "") + " this week");

        // Populate last grade (basic implementation)
        populateLastGrade(dataManager);

        // Populate date container with unique homework due dates
        List<Calendar> homeworkDates = dataManager.getUniqueHomeworkDates();
        populateDateElements(homeworkDates);

        return view;
    }

    private int getTotalAssignments(DataManager dataManager) {
        int total = 0;
        for (Subject subject : dataManager.getSubjects()) {
            total += subject.getHomeworks().size();
        }
        return total;
    }

    private int getAssignmentsThisWeek(DataManager dataManager) {
        int count = 0;
        Calendar now = Calendar.getInstance();
        // Set to start of the week (Monday)
        now.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        long weekStartMillis = now.getTimeInMillis();
        // Set to end of the week (Sunday)
        now.add(Calendar.DAY_OF_WEEK, 6);
        long weekEndMillis = now.getTimeInMillis();

        for (Subject subject : dataManager.getSubjects()) {
            for (Homework homework : subject.getHomeworks()) {
                Calendar homeworkDate = Calendar.getInstance();
                homeworkDate.setTime(homework.getDueDate());
                homeworkDate.set(Calendar.HOUR_OF_DAY, 0);
                homeworkDate.set(Calendar.MINUTE, 0);
                homeworkDate.set(Calendar.SECOND, 0);
                homeworkDate.set(Calendar.MILLISECOND, 0);
                long homeworkMillis = homeworkDate.getTimeInMillis();

                if (homeworkMillis >= weekStartMillis && homeworkMillis <= weekEndMillis) {
                    count++;
                }
            }
        }
        return count;
    }

    private void populateLastGrade(DataManager dataManager) {
        // Find the most recent grade across all subjects
        Grade latestGrade = null;
        Subject latestSubject = null;
        for (Subject subject : dataManager.getSubjects()) {
            for (Grade grade : subject.getGrades()) {
                if (latestGrade == null || grade.getScore() > latestGrade.getScore()) { // Example: Use score as proxy for recency
                    latestGrade = grade;
                    latestSubject = subject;
                }
            }
        }

        if (latestGrade != null && latestSubject != null) {
            lastGradeSubject.setText("Last graded subject: " + latestSubject.getName());
            lastGradeSubjectGrade.setText("Last grade: " + latestGrade.getDescription());
        } else {
            lastGradeSubject.setText("Last graded subject: None");
            lastGradeSubjectGrade.setText("Last grade: None");
        }
    }

    private void populateDateElements(List<Calendar> dates) {
        dateContainer.removeAllViews();

        int maxDates = Math.min(dates.size(), 7);
        float weight = maxDates > 0 ? 1.0f / maxDates : 1.0f;

        for (int i = 0; i < maxDates; i++) {
            Calendar date = dates.get(i);

            TextView dateView = new TextView(requireContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    weight
            );
            params.setMargins(4, 0, 4, 0);
            dateView.setLayoutParams(params);
            dateView.setText(dateFormat.format(date.getTime()));
            dateView.setPadding(16, 8, 16, 8);
            dateView.setBackgroundResource(R.drawable.date_background);
            dateView.setTextSize(16);
            dateView.setGravity(android.view.Gravity.CENTER);

            dateContainer.addView(dateView);
        }
    }
}