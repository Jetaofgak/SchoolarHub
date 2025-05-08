package com.example.schoolapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DashboardFragment extends Fragment {

    Button addClassButton;
    Button addTeacherButton;
    Button addStudentButton;
    Button addSubjectButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Initialize buttons
        addClassButton = view.findViewById(R.id.addClassButton);
        addTeacherButton = view.findViewById(R.id.addTeacherButton);
        addStudentButton = view.findViewById(R.id.addStudentButton);
        addSubjectButton = view.findViewById(R.id.addSubjectButton);

        // Set click listeners
        addClassButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddClassActivity.class);
            startActivity(intent);
        });

        addTeacherButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddTeacherActivity.class);
            startActivity(intent);
        });

        addStudentButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddStudentActivity.class);
            startActivity(intent);
        });

        addSubjectButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddSubject.class);
            startActivity(intent);
        });
        TextView numberOfTeachers = view.findViewById(R.id.numberOfTeachers);
        TextView numberOfStudents = view.findViewById(R.id.numberOfStudents);
        TextView numberOfGroups = view.findViewById(R.id.numberOfGroups);

        DataManager dataManager = DataManager.getInstance();

        int teacherCount = dataManager.getTeachers().size();
        int studentCount = dataManager.getStudents().size();
        int groupCount = dataManager.getGroups().size();

        numberOfTeachers.setText("Number of Teachers: " + teacherCount);
        numberOfStudents.setText("Number of Students: " + studentCount);
        numberOfGroups.setText("Number of Groups: " + groupCount);
        return view;
    }
}