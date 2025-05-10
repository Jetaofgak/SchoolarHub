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
import com.example.schoolapp.model.Teacher;
import com.example.schoolapp.service.DataManager;

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
                R.array.teacher_search_type_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchTypeSpinner.setAdapter(adapter);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        allTeachers = DataManager.getInstance().getTeachers();
        Collections.sort(allTeachers, (t1, t2) -> t1.getFirstname().compareToIgnoreCase(t2.getFirstname()));
        teacherAdapter = new TeacherAdapter(allTeachers);
        recyclerView.setAdapter(teacherAdapter);
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

        List<Teacher> filtered = new ArrayList<>();
        for (Teacher teacher : allTeachers) {
            boolean matches = false;
            switch (searchType) {
                case 0: // Name
                    matches = teacher.getFirstname().toLowerCase().contains(query) ||
                            teacher.getLastname().toLowerCase().contains(query);
                    break;
                case 1: // Age
                    matches = String.valueOf(teacher.getAge()).contains(query);
                    break;
            }
            if (matches) filtered.add(teacher);
        }
        teacherAdapter.updateTeachers(filtered);
    }
}