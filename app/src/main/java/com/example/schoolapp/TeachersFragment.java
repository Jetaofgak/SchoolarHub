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

import com.example.schoolapp.DataManager;
import com.example.schoolapp.R;
import com.example.schoolapp.Teacher;
import com.example.schoolapp.TeacherAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TeachersFragment extends Fragment {

    private Spinner searchTypeSpinner;
    private EditText searchEditText;
    private RecyclerView recyclerView;
    private TeacherAdapter teacherAdapter;
    private List<Teacher> allTeachers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teachers, container, false);

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
                    R.array.teacher_search_type_array,
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
            allTeachers = DataManager.getInstance().getTeachers();

            if (allTeachers == null) {
                allTeachers = new ArrayList<>();
                Toast.makeText(getContext(), "No teachers data available", Toast.LENGTH_SHORT).show();
            }

            Collections.sort(allTeachers, (s1, s2) -> s1.name.compareToIgnoreCase(s2.name));
            teacherAdapter = new TeacherAdapter(allTeachers);
            recyclerView.setAdapter(teacherAdapter);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error setting up teacher list: " + e.getMessage(), Toast.LENGTH_LONG).show();
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

        teacherAdapter = new TeacherAdapter(filteredList);
        recyclerView.setAdapter(teacherAdapter);
    }
}