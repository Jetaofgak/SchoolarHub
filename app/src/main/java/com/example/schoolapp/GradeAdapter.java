package com.example.schoolapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeViewHolder> {

    private List<Subject> subjectList;

    public GradeAdapter(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }

    @NonNull
    @Override
    public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grade, parent, false);
        return new GradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
        // Looping through subjects and their grades one by one
        int count = 0;
        for (Subject subject : subjectList) {
            List<Grade> grades = subject.getGrades();
            if (position < count + grades.size()) {
                Grade grade = grades.get(position - count);
                holder.descriptionView.setText(subject.getName() + ": " + grade.getDescription());
                holder.dateOrNoteView.setText("Note: " + grade.getScore());
                return;
            }
            count += grades.size();
        }
    }

    @Override
    public int getItemCount() {
        int total = 0;
        for (Subject subject : subjectList) {
            total += subject.getGrades().size();
        }
        return total;
    }

    static class GradeViewHolder extends RecyclerView.ViewHolder {
        TextView descriptionView;
        TextView dateOrNoteView;

        GradeViewHolder(View itemView) {
            super(itemView);
            descriptionView = itemView.findViewById(R.id.descriptionView);
            dateOrNoteView = itemView.findViewById(R.id.dateOrNoteView);
        }
    }
}
