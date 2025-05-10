package com.example.schoolapp.adminPart;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.model.Student;
import com.example.schoolapp.service.DataManager;

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
        setupSearchListeners(view);

        return view;
    }

    private void initializeViews(View view) {
        searchTypeSpinner = view.findViewById(R.id.searchTypeSpinner);
        searchEditText = view.findViewById(R.id.searchEditText);
        recyclerView = view.findViewById(R.id.recyclerViewLister);
    }

    private void setupSpinnerAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.student_search_type_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchTypeSpinner.setAdapter(adapter);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        allStudents = DataManager.getInstance().getStudents();
        Collections.sort(allStudents, (s1, s2) -> s1.getFirstname().compareToIgnoreCase(s2.getFirstname()));
        studentAdapter = new StudentAdapter(allStudents);
        recyclerView.setAdapter(studentAdapter);
    }

    private void setupSearchListeners(View view) {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch();
            }
            @Override public void afterTextChanged(Editable s) {}
        });


    }

    private void performSearch() {
        String query = searchEditText.getText().toString().toLowerCase();
        int searchType = searchTypeSpinner.getSelectedItemPosition();

        List<Student> filtered = new ArrayList<>();
        for (Student student : allStudents) {
            boolean matches = false;
            switch (searchType) {
                case 0: // Name
                    matches = student.getFirstname().toLowerCase().contains(query) ||
                            student.getLastname().toLowerCase().contains(query);
                    break;
                case 1: // Age
                    matches = String.valueOf(student.getAge()).contains(query);
                    break;
                case 2: // Level
                    matches = String.valueOf(student.getGroup().getLevel().getLvl()).contains(query);
                    break;
                case 3: // Group
                    matches = String.valueOf(student.getGroup().getNumeric()).contains(query);
                    break;
            }
            if (matches) filtered.add(student);
        }
        studentAdapter.updateStudents(filtered);
    }
}