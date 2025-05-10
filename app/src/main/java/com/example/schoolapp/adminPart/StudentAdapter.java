package com.example.schoolapp.adminPart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.model.Student;
import com.example.schoolapp.model.Group;
import com.example.schoolapp.model.LevelOfGroups;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private List<Student> students;

    public StudentAdapter(List<Student> students) {
        this.students = students;
    }
    public void updateStudents(List<Student> filteredStudents) {
        this.students = filteredStudents;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_card_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = students.get(position);
        Group group = student.getGroup();
        LevelOfGroups level = group != null ? group.getLevel() : null;

        holder.bind(
                student.getFirstname() + " " + student.getLastname(),
                student.getAge(),
                group != null ? group.getNumeric() : 0,
                level != null ? level.getLvl() : -1
        );
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameView, ageView, groupView, levelView;

        public StudentViewHolder(View view) {
            super(view);
            nameView = view.findViewById(R.id.textViewStudentName);
            ageView = view.findViewById(R.id.textViewStudentAge);
            groupView = view.findViewById(R.id.textViewGroupName);
            levelView = view.findViewById(R.id.textViewGroupLvl);
        }

        public void bind(String name, int age, int groupNumber, int level) {
            nameView.setText("Name: " + name);
            ageView.setText("Age: " + age);
            groupView.setText("Group: " + (groupNumber != -1 ? groupNumber : "N/A"));
            levelView.setText("Level: " + (level != -1 ? level : "N/A"));
        }
    }
}
