package com.example.schoolapp.studentPart;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.model.Homework;
import com.example.schoolapp.model.Subject;
import com.example.schoolapp.service.DataManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AssignementFragment extends Fragment {

    private EditText searchEditText;
    private Spinner searchTypeSpinner;
    private HomeworkAdapter adapter;
    private List<Homework> allHomework;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignements, container, false);

        searchEditText = view.findViewById(R.id.searchEditText);
        searchTypeSpinner = view.findViewById(R.id.searchTypeSpinner);
        RecyclerView recyclerView = view.findViewById(R.id.assignmentRecyclerView);

        allHomework = new ArrayList<>();
        for (Subject subject : DataManager.getInstance().getSubjects()) {
            allHomework.addAll(subject.getHomeworks());
        }
        Collections.sort(allHomework, Comparator.comparing(Homework::getDueDate));

        adapter = new HomeworkAdapter(allHomework);
        recyclerView.setAdapter(adapter);

        setupSearchSpinner();
        setupSearchListeners(view);

        return view;
    }

    private void setupSearchSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.homework_search_type_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchTypeSpinner.setAdapter(adapter);
    }

    private void setupSearchListeners(View view) {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                performSearch();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        Button searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(v -> performSearch());
    }

    private void performSearch() {
        String query = searchEditText.getText().toString().toLowerCase();
        int searchType = searchTypeSpinner.getSelectedItemPosition();

        List<Homework> filtered = new ArrayList<>();
        for (Homework hw : allHomework) {
            boolean matches = false;
            switch (searchType) {
                case 0: // Subject
                    matches = hw.getSubject().getTitle().toLowerCase().contains(query);
                    break;
                case 1: // Description
                    matches = hw.getDescription().toLowerCase().contains(query);
                    break;
                case 2: // Due Date
                    matches = hw.getDueDate().toString().toLowerCase().contains(query);
                    break;
            }
            if (matches) filtered.add(hw);
        }
        adapter.updateHomeworkList(filtered);
    }
}