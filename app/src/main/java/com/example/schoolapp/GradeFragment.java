package com.example.schoolapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GradeFragment extends Fragment {

    public GradeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grades, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.gradesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        GradeAdapter adapter = new GradeAdapter(DataManager.getInstance().getSubjects());
        recyclerView.setAdapter(adapter);

        return view;
    }
}
