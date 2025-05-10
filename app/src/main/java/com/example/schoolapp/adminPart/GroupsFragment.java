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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.model.Group;
import com.example.schoolapp.service.DataManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupsFragment extends Fragment {

    private Spinner searchTypeSpinner;
    private EditText searchEditText;
    private RecyclerView recyclerView;
    private GroupAdapter groupAdapter;
    private List<Group> allGroups;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        initializeViews(view);
        setupSpinnerAdapter();
        setupRecyclerView();
        setupSearchListeners(view);

        return view;
    }

    private void initializeViews(View view) {
        try {
            searchTypeSpinner = view.findViewById(R.id.searchTypeSpinner);
            searchEditText = view.findViewById(R.id.searchEditText);
            recyclerView = view.findViewById(R.id.recyclerViewLister);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error initializing views: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void setupSpinnerAdapter() {
        try {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    getContext(),
                    R.array.group_search_type_array,
                    android.R.layout.simple_spinner_item
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            searchTypeSpinner.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error setting up spinner: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void setupRecyclerView() {
        try {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            allGroups = DataManager.getInstance().getGroups();
            Collections.sort(allGroups, (g1, g2) ->
                    String.valueOf(g1.getNumeric()).compareToIgnoreCase(String.valueOf(g2.getNumeric()))
            );
            groupAdapter = new GroupAdapter(allGroups);
            recyclerView.setAdapter(groupAdapter);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error setting up group list: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
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

        List<Group> filtered = new ArrayList<>();
        for (Group group : allGroups) {
            boolean matches = false;
            switch (searchType) {
                case 0: // Numeric
                    matches = String.valueOf(group.getNumeric()).contains(query);
                    break;
                case 1: // Level
                    matches = String.valueOf(group.getLevel().getLvl()).contains(query);
                    break;
            }
            if (matches) filtered.add(group);
        }
        groupAdapter.updateGroups(filtered);
    }
}