package com.example.schoolapp.teacherPart;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.schoolapp.R;
import com.example.schoolapp.studentPart.AssignementFragment;
import com.example.schoolapp.studentPart.DashboardFragmentStudent;
import com.example.schoolapp.studentPart.GradeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DashboardActivityTeacherMain extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_container_teach);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_teacher);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_dashboard_teach) {
                selectedFragment = new DashboardFragmentTeacher();
            } else if (itemId == R.id.nav_profile_teach) {
                selectedFragment = new ProfileFragment();
            }
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_teacher, selectedFragment)
                        .commit();
            }
            return true;
        });
    }
}
