package com.example.schoolapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DashboardActivityStudentMain extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container_stud);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_student);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_dashboard) {
                selectedFragment = new DashboardFragmentStudent();
            } else if (itemId == R.id.nav_assignement) {
                selectedFragment = new AssignementFragment();
            } else if (itemId == R.id.nav_grade) {
                selectedFragment = new GradeFragment();
            } else if (itemId == R.id.nav_profile) {
                selectedFragment = new GroupsFragment(); // TO BE CHANGED
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_student, selectedFragment)
                        .commit();
            }
            return true;
        });
    }

    // Add any additional methods or functionality as needed
}
