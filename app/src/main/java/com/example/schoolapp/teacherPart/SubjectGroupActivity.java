package com.example.schoolapp.teacherPart;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.schoolapp.R;
import com.example.schoolapp.model.Group;
import com.example.schoolapp.model.Homework;
import com.example.schoolapp.model.Subject;
import com.example.schoolapp.service.DataManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SubjectGroupActivity extends AppCompatActivity {

    private LinearLayout groupsContainer;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_group);

        // Initialize the container
        groupsContainer = findViewById(R.id.groupsContainer);

        // Get the subject ID from the Intent
        int subjectId = getIntent().getIntExtra("subject_id", -1);

        if (subjectId != -1) {
            // Fetch the subject using the subject ID
            Subject selectedSubject = findSubjectById(subjectId);

            if (selectedSubject != null) {
                displayGroups(selectedSubject.getGroups(), selectedSubject.getTitle(), selectedSubject);
            } else {
                // Display a single TextView for subject not found
                addTextViewToContainer("Subject not found.");
            }
        } else {
            // Display a single TextView for no subject ID
            addTextViewToContainer("No subject ID provided.");
        }
    }

    // Helper method to find the Subject by its ID using DataManager
    private Subject findSubjectById(int id) {
        for (Subject subject : DataManager.getInstance().getSubjects()) {
            if (subject.getId() == id) {
                return subject;
            }
        }
        return null;
    }

    // Helper method to display the groups by generating TextViews
    private void displayGroups(List<Group> groups, String subjectTitle, Subject subject) {
        // Clear any existing views in the container
        groupsContainer.removeAllViews();

        // Add a header TextView for the subject title
        addTextViewToContainer("Groups for " + subjectTitle + ":", true);

        if (groups.isEmpty()) {
            // Add a TextView indicating no groups
            addTextViewToContainer("No groups assigned to this subject.");
        } else {
            // Add a clickable TextView for each group
            for (Group group : groups) {
                String groupText = "- Group ID: " + group.getId() +
                        ", Name: " + group.getTime() +
                        ", Level: " + group.getLevel().getDescription();
                addTextViewToContainer(groupText, false, group, subject);
            }
        }
    }

    // Helper method to create and add a TextView to the container
    private void addTextViewToContainer(String text) {
        addTextViewToContainer(text, false, null, null);
    }

    private void addTextViewToContainer(String text, boolean isHeader) {
        addTextViewToContainer(text, isHeader, null, null);
    }

    private void addTextViewToContainer(String text, boolean isHeader, Group group, Subject subject) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(isHeader ? 20 : 16); // Larger text for header
        textView.setPadding(8, 8, 8, 8);

        // Set the outlined box background (except for header)
        if (!isHeader) {
            textView.setBackgroundResource(R.drawable.outlined_box);
        }

        // Set layout parameters
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 8, 0, 8); // Add vertical spacing
        textView.setLayoutParams(params);

        // Apply bold style for header
        if (isHeader) {
            textView.setTypeface(null, android.graphics.Typeface.BOLD);
        }

        // Make TextView clickable for groups
        if (group != null && subject != null) {
            textView.setClickable(true);
            textView.setOnClickListener(v -> showAddHomeworkDialog(group, subject));
        }

        // Add the TextView to the container
        groupsContainer.addView(textView);
    }

    // Show dialog to add a new homework
    private void showAddHomeworkDialog(Group group, Subject subject) {
        // Create a dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Homework for Group " + group.getTime());

        // Create a LinearLayout to hold input fields
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16, 16, 16, 16);

        // Description input
        EditText descriptionInput = new EditText(this);
        descriptionInput.setHint("Homework Description");
        layout.addView(descriptionInput);

        // Deadline input
        EditText deadlineInput = new EditText(this);
        deadlineInput.setHint("Deadline (YYYY-MM-DD)");
        layout.addView(deadlineInput);

        builder.setView(layout);

        // Add button
        builder.setPositiveButton("Add", (dialog, which) -> {
            String description = descriptionInput.getText().toString().trim();
            String deadlineStr = deadlineInput.getText().toString().trim();

            if (description.isEmpty() || deadlineStr.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                // Parse the deadline
                Date deadline = dateFormat.parse(deadlineStr);

                // Generate a unique homework ID
                int homeworkId = generateUniqueHomeworkId();

                // Create new homework
                Homework homework = new Homework(homeworkId, description, deadline);
                homework.setSubject(subject); // This adds the homework to the subject's homeworks list

                Toast.makeText(this, "Homework added successfully", Toast.LENGTH_SHORT).show();
            } catch (ParseException e) {
                Toast.makeText(this, "Invalid date format. Use YYYY-MM-DD", Toast.LENGTH_SHORT).show();
            }
        });

        // Cancel button
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.show();
    }



    // Generate a unique homework ID
    private int generateUniqueHomeworkId() {
        int maxId = 0;
        for (Subject s : DataManager.getInstance().getSubjects()) {
            for (Homework h : s.getHomeworks()) {
                maxId = Math.max(maxId, h.getId());
            }
        }
        return maxId + 1;
    }

    // Method for the Return button click
    public void onReturnButtonClick(android.view.View view) {
        finish(); // Close the activity
    }
}