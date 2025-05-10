package com.example.schoolapp.teacherPart;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.schoolapp.R;
import com.example.schoolapp.model.Subject;
import com.example.schoolapp.model.Teacher;
import com.example.schoolapp.service.DataManager;

public class DashboardFragmentTeacher extends Fragment {
    LinearLayout layout;
    TextView teacherName;
    LinearLayout subjects;

    Button gradeButton,assignementLookButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_teacher, container, false);
        layout = view.findViewById(R.id.teacherDashboardLayout);
        // Get the first teacher from DataManager
        subjects = view.findViewById(R.id.subjectsContainer);

        teacherName = view.findViewById(R.id.teacher_name);

        gradeButton = view.findViewById(R.id.giveGradeButton);
        assignementLookButton = view.findViewById(R.id.lookAssignementButton);
        Teacher firstTeacher = DataManager.getInstance().getTeachers().get(0);
        gradeButton.setOnClickListener(v -> {
            // Show dummy grade dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Grade to give to Taoufik");

            // Create input field
            LinearLayout layout = new LinearLayout(getContext());
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(16, 16, 16, 16);

            EditText gradeInput = new EditText(getContext());
            gradeInput.setHint("Grade (0-100)");
            gradeInput.setInputType(android.text.InputType.TYPE_CLASS_NUMBER | android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL);
            layout.addView(gradeInput);

            builder.setView(layout);

            // Add button
            builder.setPositiveButton("Add", (dialog, which) -> {
                String gradeStr = gradeInput.getText().toString().trim();
                if (gradeStr.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter a grade", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getContext(), "Grade entered: " + gradeStr, Toast.LENGTH_SHORT).show();
            });

            // Cancel button
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            builder.show();
        });
        assignementLookButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), assignement_returned.class);
            startActivity(intent);
        });
        if (firstTeacher != null) {
            // Display teacher info
            TextView teacherName = view.findViewById(R.id.teacher_name);
            teacherName.setText(firstTeacher.getFirstname() + " " + firstTeacher.getLastname());

            // Setup subject buttons
            setupSubjectButtons(view, firstTeacher);
        } else {
            Toast.makeText(getContext(), "No teacher data available", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    private void setupSubjectButtons(View rootView, Teacher teacher) {
        LinearLayout container = rootView.findViewById(R.id.subjectsContainer);
        container.removeAllViews(); // Clear existing views

        if (teacher.getSubjects().isEmpty()) {
            // Show message if no subjects
            Button noSubjectsButton = new Button(getContext());
            noSubjectsButton.setText("No Subjects Assigned");
            noSubjectsButton.setEnabled(false);
            container.addView(noSubjectsButton);
            return;
        }

        for (Subject subject : teacher.getSubjects()) {
            Button subjectButton = new Button(getContext());

            // Set button properties
            subjectButton.setText(subject.getTitle());
            subjectButton.setAllCaps(false);
            subjectButton.setTextSize(16);

            // Set layout parameters
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(32, 16, 32, 16); // Add margins
            subjectButton.setLayoutParams(params);

            // Style the button (you can customize this)
            subjectButton.setBackgroundResource(R.drawable.rounded_button);
            subjectButton.setTextColor(getResources().getColor(android.R.color.white));

            // Add click listener
            subjectButton.setOnClickListener(v -> {
                onSubjectSelected(subject);
            });

            container.addView(subjectButton);
        }
    }

    private void onSubjectSelected(Subject subject) {
        // Create a new Intent to start the target Activity
        Intent intent = new Intent(getContext(), SubjectGroupActivity.class); // Replace SubjectGroupsActivity with the actual name of your target Activity

        // Pass the subject ID to the Activity using an extra
        intent.putExtra("subject_id", subject.getId());

        // Start the new Activity
        startActivity(intent);
    }
}
