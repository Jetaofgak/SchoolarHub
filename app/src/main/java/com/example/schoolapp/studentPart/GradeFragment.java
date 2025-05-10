package com.example.schoolapp.studentPart;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.model.Grade;
import com.example.schoolapp.model.Student;
import com.example.schoolapp.service.DataManager;

import java.util.ArrayList;
import java.util.List;

public class GradeFragment extends Fragment {

    private GradeAdapter adapter;
    private List<Grade> allGrades;
    private EditText searchEditText;
    private Spinner searchTypeSpinner;
    private Button searchButton;

    public GradeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grades, container, false);

        // Initialize views
        searchEditText = view.findViewById(R.id.searchEditText);
        searchTypeSpinner = view.findViewById(R.id.searchTypeSpinner);
        searchButton = view.findViewById(R.id.searchButton);

        // Setup RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.gradesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Get all grades
        allGrades = getAllGrades();
        adapter = new GradeAdapter(allGrades);
        recyclerView.setAdapter(adapter);

        // Setup search spinner
        setupSearchSpinner();

        // Setup search functionality
        setupSearchListeners();

        return view;
    }

    private List<Grade> getAllGrades() {
        List<Grade> grades = new ArrayList<>();
        DataManager dataManager = DataManager.getInstance();

        // Get grades from all students
        for (Student student : dataManager.getStudents()) {
            grades.addAll(student.getGrades());
        }
        return grades;
    }

    private void setupSearchSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.grade_search_type_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchTypeSpinner.setAdapter(adapter);
    }

    private void setupSearchListeners() {
        // Text change listener
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterGrades();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Spinner selection listener
        searchTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterGrades();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Search button click listener
        searchButton.setOnClickListener(v -> filterGrades());
    }

    private void filterGrades() {
        String query = searchEditText.getText().toString().toLowerCase();
        int searchType = searchTypeSpinner.getSelectedItemPosition();

        List<Grade> filteredGrades = new ArrayList<>();

        for (Grade grade : allGrades) {
            boolean matches = false;

            switch (searchType) {
                case 0: // Subject
                    matches = grade.getSubject().getTitle().toLowerCase().contains(query);
                    break;
                case 1: // Description
                    matches = grade.getDescription().toLowerCase().contains(query);
                    break;
                case 2: // Grade value
                    matches = String.valueOf(grade.getNote()).contains(query);
                    break;
            }

            if (matches) {
                filteredGrades.add(grade);
            }
        }

        adapter.updateGrades(filteredGrades);
    }
}