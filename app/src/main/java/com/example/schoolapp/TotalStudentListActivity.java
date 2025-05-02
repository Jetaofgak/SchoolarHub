package com.example.schoolapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.util.List;

public class TotalStudentListActivity extends AppCompatActivity {
    Spinner spinner ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_student_list);
        spinner = findViewById(R.id.searchTypeSpinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this, R.array.search_types_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);


        RecyclerView recyclerView = findViewById(R.id.recyclerViewStudents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Student> studentList = DataManager.getInstance().getStudents();
        Collections.sort(studentList, (s1, s2) -> s1.name.compareToIgnoreCase(s2.name));

        StudentAdapter adapter = new StudentAdapter(studentList);
        recyclerView.setAdapter(adapter);
    }
}
