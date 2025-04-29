package com.example.schoolapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TotalTeacherListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_total_teacher_list);

        List<Teacher> allTeachers = DataManager.getInstance().getTeachers();
        allTeachers.sort((t1, t2) -> t1.name.compareToIgnoreCase(t2.name));

        RecyclerView recyclerView = findViewById(R.id.teacherRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TeacherAdapter(allTeachers));
    }
}