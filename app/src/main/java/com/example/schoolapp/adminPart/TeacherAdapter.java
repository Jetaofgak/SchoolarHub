package com.example.schoolapp.adminPart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.model.Teacher;
import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {
    private List<Teacher> teachers;

    public TeacherAdapter(List<Teacher> teachers) {
        this.teachers = teachers;
    }
    public void updateTeachers(List<Teacher> filteredTeachers) {
        this.teachers = filteredTeachers;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_teacher, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        Teacher teacher = teachers.get(position);
        holder.bind(
                teacher.getFirstname() + " " + teacher.getLastname(),
                teacher.getId(),
                teacher.getEmail(),
                teacher.getAge()
        );
    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    static class TeacherViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameView, idView, emailView, ageView;

        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.tvTeacherName);
            idView = itemView.findViewById(R.id.tvTeacherId);
            emailView = itemView.findViewById(R.id.tvTeacherEmail);
            ageView = itemView.findViewById(R.id.tvTeacherAge);
        }

        public void bind(String name, long id, String email, int age) {
            nameView.setText(name);
            idView.setText("ID: " + id);
            emailView.setText(email);
            ageView.setText("Age: " + age);
        }
    }
}