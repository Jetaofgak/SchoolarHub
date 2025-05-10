package com.example.schoolapp.studentPart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.model.Grade;

import java.util.List;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeViewHolder> {

    private List<Grade> grades;

    public GradeAdapter(List<Grade> grades) {
        this.grades = grades;
    }

    public void updateGrades(List<Grade> newGrades) {
        this.grades = newGrades;
        notifyDataSetChanged();
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
        Grade grade = grades.get(position);
        holder.bind(grade);
    }

    @Override
    public int getItemCount() {
        return grades.size();
    }

    static class GradeViewHolder extends RecyclerView.ViewHolder {
        private final TextView descriptionView;
        private final TextView dateOrNoteView;

        public GradeViewHolder(View itemView) {
            super(itemView);
            descriptionView = itemView.findViewById(R.id.descriptionView);
            dateOrNoteView = itemView.findViewById(R.id.dateOrNoteView);
        }

        public void bind(Grade grade) {
            descriptionView.setText(String.format("%s: %s",
                    grade.getSubject().getTitle(),
                    grade.getDescription()));

            dateOrNoteView.setText(String.format("Note: %.1f", grade.getNote()));
        }
    }
}