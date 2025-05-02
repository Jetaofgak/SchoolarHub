package com.example.schoolapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private final List<Student> studentList;
    private List<GroupClass> groupList;
    String gName = "Unknown";
    int lvl = -1;

    public StudentAdapter(List<Student> studentList) {
        this.studentList = studentList;
        groupList = DataManager.getInstance().getGroups();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, age, groupName, groupLvl;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.textViewStudentName);
            age = view.findViewById(R.id.textViewStudentAge);
            groupName = view.findViewById(R.id.textViewGroupName);
            groupLvl = view.findViewById(R.id.textViewGroupLvl);
        }
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, int position) {
        Student s = studentList.get(position);
        //verifie si le student est dans l'un de ses group.
        for(GroupClass group: groupList)
        {


           gName = group.getRightGroupForStudent(s).first;
           lvl = group.getRightGroupForStudent(s).second;
           if("NoFound".equals(gName) && lvl == 0)
           {
               continue;
           }
            holder.groupName.setText("Groupe and Level: Groupe: "+gName + " "+ lvl);
        }
        holder.name.setText("Name: " + s.name);
        holder.age.setText("Age: " + s.Age);

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }
}
