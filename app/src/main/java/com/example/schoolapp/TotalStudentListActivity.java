package com.example.schoolapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TotalStudentListActivity extends AppCompatActivity {

    private Spinner searchTypeSpinner;
    private EditText searchEditText;
    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private List<Student> allStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_student_list);

        // Initialize views
        searchTypeSpinner = findViewById(R.id.searchTypeSpinner);
        searchEditText = findViewById(R.id.searchEditText);
        Button searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.recyclerViewStudents);

        // Spinner setup
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.search_types_array,
                android.R.layout.simple_spinner_item
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchTypeSpinner.setAdapter(spinnerAdapter);

        // RecyclerView setup
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        allStudents = DataManager.getInstance().getStudents();
        Collections.sort(allStudents, (s1, s2) -> s1.name.compareToIgnoreCase(s2.name));

        // Set initial full list
        studentAdapter = new StudentAdapter(allStudents);
        recyclerView.setAdapter(studentAdapter);

        // Search button logic
        searchButton.setOnClickListener(v -> performSearch());
    }

    private void performSearch() {
        String searchText = searchEditText.getText().toString().trim().toLowerCase();
        String selectedType = searchTypeSpinner.getSelectedItem().toString();

        List<Student> filteredList = new ArrayList<>();

        for (Student student : allStudents) {
            switch (selectedType) {
                case "Name":
                    if (student.name.toLowerCase().startsWith(searchText)) {
                        filteredList.add(student);
                    }
                    break;

                case "Level":
                    if (student.group != null &&
                            student.group.getLvl() == Integer.parseInt(searchText)) {
                        filteredList.add(student);
                    }
                    break;
                case "Age":
                    if (student.Age == Integer.parseInt(searchText)) {
                        filteredList.add(student);
                    }
                    break;
                        case "Group":
                    if (student.group != null &&
                            student.group.name.toLowerCase().startsWith(searchText)) {
                        filteredList.add(student);
                    }
                    break;
            }
        }

        // Update adapter with filtered list
        studentAdapter = new StudentAdapter(filteredList);
        recyclerView.setAdapter(studentAdapter);
    }
}
