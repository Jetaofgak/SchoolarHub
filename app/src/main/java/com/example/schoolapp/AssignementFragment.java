package com.example.schoolapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AssignementFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_assignements, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.assignmentRecyclerView);

        // Aggregate all homeworks
        List<Homework> allHomework = new ArrayList<>();
        for (Subject subject : DataManager.getInstance().getSubjects()) {
            allHomework.addAll(subject.getHomeworks());
        }

        // Sort by due date
        Collections.sort(allHomework, Comparator.comparing(Homework::getDueDate));

        HomeworkAdapter adapter = new HomeworkAdapter(allHomework);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
