package com.example.schoolapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentsFragment extends Fragment {

    private Spinner searchTypeSpinner;
    private EditText searchEditText;
    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private List<Student> allStudents;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_students, container, false);

        initializeViews(view);
        setupSpinnerAdapter();
        setupRecyclerView();
        setupClickListeners(view);

        return view;
    }

    private void initializeViews(View view) {
        try {
            searchTypeSpinner = view.findViewById(R.id.searchTypeSpinner);
            searchEditText = view.findViewById(R.id.searchEditText);
            recyclerView = view.findViewById(R.id.recyclerViewLister);

            if (searchTypeSpinner == null || searchEditText == null || recyclerView == null) {
                throw new RuntimeException("One or more views not found in layout");
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error initializing views: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void setupSpinnerAdapter() {
        try {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    getContext(),
                    R.array.student_search_type_array, // Replace with your specific array
                    android.R.layout.simple_spinner_item
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            searchTypeSpinner.setAdapter(adapter);
            searchTypeSpinner.setSelection(0);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error setting up spinner: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void setupRecyclerView() {
        try {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            allStudents = DataManager.getInstance().getStudents();

            if (allStudents == null) {
                allStudents = new ArrayList<>();
                Toast.makeText(getContext(), "No students data available", Toast.LENGTH_SHORT).show();
            }

            Collections.sort(allStudents, (s1, s2) -> s1.name.compareToIgnoreCase(s2.name));
            studentAdapter = new StudentAdapter(allStudents);
            recyclerView.setAdapter(studentAdapter);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error setting up student list: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void setupClickListeners(View view) {
        try {
            Button searchButton = view.findViewById(R.id.searchButton);
            searchButton.setOnClickListener(v -> {
                try {
                    performSearch();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Search error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error setting up click listeners: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
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

                case "Age":
                    if (student.Age == Integer.parseInt(searchText)) {
                        filteredList.add(student);
                    }
                    break;
                case "Level":
                    if(student.group != null && student.group.getLvl() == Integer.parseInt(searchText)) {
                        filteredList.add(student);
                    }
                case "Group":
                    if (student.group != null &&
                            student.group.name.toLowerCase().contains(searchText.toLowerCase())) {
                        filteredList.add(student);
                    }
                    break;
            }
        }

        studentAdapter = new StudentAdapter(filteredList);
        recyclerView.setAdapter(studentAdapter);
    }
}