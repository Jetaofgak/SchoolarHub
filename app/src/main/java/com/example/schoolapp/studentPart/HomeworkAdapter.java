package com.example.schoolapp.studentPart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.model.Homework;
import com.example.schoolapp.model.Subject;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.HomeworkViewHolder> {
    private List<Homework> homeworkList;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
    public void updateHomeworkList(List<Homework> filteredHomework) {
        this.homeworkList = filteredHomework;
        notifyDataSetChanged();
    }
    public HomeworkAdapter(List<Homework> homeworkList) {
        this.homeworkList = homeworkList;
    }

    @NonNull
    @Override
    public HomeworkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_homework, parent, false);
        return new HomeworkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeworkViewHolder holder, int position) {
        Homework homework = homeworkList.get(position);
        Subject subject = homework.getSubject();

        holder.bind(
                homework.getDescription(),
                subject != null ? subject.getTitle() : "No Subject",
                dateFormat.format(homework.getDueDate())
        );
    }

    @Override
    public int getItemCount() {
        return homeworkList.size();
    }

    static class HomeworkViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView, subjectView, dueDateView;

        public HomeworkViewHolder(View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.homeworkTitle);
            subjectView = itemView.findViewById(R.id.homeworkSubject);
            dueDateView = itemView.findViewById(R.id.homeworkDueDate);
        }

        public void bind(String title, String subject, String dueDate) {
            titleView.setText(title);
            subjectView.setText(subject);
            dueDateView.setText("Due: " + dueDate);
        }
    }
}