package com.example.schoolapp.studentPart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.schoolapp.R;
import com.example.schoolapp.model.Group;
import com.example.schoolapp.model.Student;
import com.example.schoolapp.service.DataManager;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize UI components
        TextView studentName = view.findViewById(R.id.studentName);
        TextView classInfo = view.findViewById(R.id.classInfo);
        Button btnEditInfo = view.findViewById(R.id.btnEditInfo);
        Button btnChangePassword = view.findViewById(R.id.btnChangePassword);

        // Set click listeners
        btnEditInfo.setOnClickListener(v -> {
            // Handle edit info click
            Toast.makeText(getContext(), "Edit Info Clicked", Toast.LENGTH_SHORT).show();
        });

        btnChangePassword.setOnClickListener(v -> {
            // Handle change password click
            Toast.makeText(getContext(), "Change Password Clicked", Toast.LENGTH_SHORT).show();
        });

        // Load student data (example - replace with your actual data)
        loadStudentData(studentName, classInfo);

        return view;
    }

    private void loadStudentData(TextView nameView, TextView classView) {
        // Get the DataManager instance
        DataManager dataManager = DataManager.getInstance();

        // Get the first student (ID 1 - Ali Khan)
        Student currentStudent = dataManager.getStudents().get(0);

        // Set the student info
        nameView.setText(currentStudent.getFirstname() + " " + currentStudent.getLastname());

        // Get group info
        Group studentGroup = currentStudent.getGroup();
        String groupInfo = "Class: " + studentGroup.getNumeric() +
                ", Group: " + studentGroup.getLevel().getLvl();

        classView.setText(groupInfo);
    }
}
