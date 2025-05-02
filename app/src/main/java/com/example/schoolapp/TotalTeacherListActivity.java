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

public class TotalTeacherListActivity extends AppCompatActivity {

    private Spinner searchTypeSpinner;
    private EditText searchEditText;
    private RecyclerView recyclerView;
    private TeacherAdapter teacherAdapter;
    private List<Teacher> allTeachers;

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

        // Create a new list excluding "Group" and "Level"
        List<String> spinnerItems = new ArrayList<>();
        for (int i = 0; i < spinnerAdapter.getCount(); i++) {
            String item = spinnerAdapter.getItem(i).toString();
            // Add only items that are not "Group" or "Level"
            if (!item.equals("Group") && !item.equals("Level")) {
                spinnerItems.add(item);
            }
        }

        // Create a new ArrayAdapter with the filtered list
        ArrayAdapter<String> newSpinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                spinnerItems
        );
        newSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchTypeSpinner.setAdapter(newSpinnerAdapter);

        // RecyclerView setup
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        allTeachers = DataManager.getInstance().getTeachers();
        Collections.sort(allTeachers, (s1, s2) -> s1.name.compareToIgnoreCase(s2.name));

        // Set initial full list
        teacherAdapter = new TeacherAdapter(allTeachers);
        recyclerView.setAdapter(teacherAdapter);

        // Search button logic
        searchButton.setOnClickListener(v -> performSearch());
    }

    private void performSearch() {
        String searchText = searchEditText.getText().toString().trim().toLowerCase();
        String selectedType = searchTypeSpinner.getSelectedItem().toString();

        List<Teacher> filteredList = new ArrayList<>();

        for (Teacher teacher : allTeachers) {
            switch (selectedType) {
                case "Name":
                    if (teacher.name.toLowerCase().startsWith(searchText)) {
                        filteredList.add(teacher);
                    }
                    break;

                case "Age":
                    if (teacher.age == Integer.parseInt(searchText)) {
                        filteredList.add(teacher);
                    }
                    break;
            }
        }

        // Update adapter with filtered list
        teacherAdapter = new TeacherAdapter(filteredList);
        recyclerView.setAdapter(teacherAdapter);
    }
}
