package com.example.schoolapp.teacherPart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.schoolapp.R;
import com.example.schoolapp.model.Teacher;
import com.example.schoolapp.service.DataManager;

public class ProfileFragment extends Fragment {
    ProfileFragment()  {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_teach, container, false);

        // Initialize UI components
        TextView teacherName = view.findViewById(R.id.teacherName);
        Button btnEditInfo = view.findViewById(R.id.btnEditInfoTeach);
        Button btnChangePassword = view.findViewById(R.id.btnChangePasswordTeach);
        // Set click listeners
        btnEditInfo.setOnClickListener(v -> {
            // Handle edit info click
            Toast.makeText(getContext(), "Edit Info Clicked", Toast.LENGTH_SHORT).show();
        });

        btnChangePassword.setOnClickListener(v -> {
            // Handle change password click
            Toast.makeText(getContext(), "Change Password Clicked", Toast.LENGTH_SHORT).show();
        });


        return view;
    }

    private void loadTeacherData(TextView nameView) {
        // Get the DataManager instance
        DataManager dataManager = DataManager.getInstance();

        // Get the first teacher (ID 1 - Ali Khan)
        Teacher currentTeacher = dataManager.getTeachers().get(0);

        // Set the teacher info
        nameView.setText(currentTeacher.getFirstname() + " " + currentTeacher.getLastname());
    }
}
