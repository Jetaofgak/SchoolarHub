package com.example.schoolapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.util.List;

public class TotalStudentListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_student_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewStudents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Student> studentList = DataManager.getInstance().getStudents();
        Collections.sort(studentList, (s1, s2) -> s1.name.compareToIgnoreCase(s2.name));

        StudentAdapter adapter = new StudentAdapter(studentList);
        recyclerView.setAdapter(adapter);
    }
}
