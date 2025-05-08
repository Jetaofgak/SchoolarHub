package com.example.schoolapp;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.HomeworkViewHolder> {

    private List<Homework> homeworkList;

    public HomeworkAdapter(List<Homework> homeworkList) {
        this.homeworkList = homeworkList;
    }

    public static class HomeworkViewHolder extends RecyclerView.ViewHolder {
        TextView title, dueDate;

        public HomeworkViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.homeworkTitle);
            dueDate = itemView.findViewById(R.id.homeworkDueDate);
        }
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
        holder.title.setText(homework.getDescription());
        holder.dueDate.setText(homework.getDueDate().toString()); // Format as needed
    }

    @Override
    public int getItemCount() {
        return homeworkList.size();
    }
}

