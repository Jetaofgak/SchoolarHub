package com.example.schoolapp.adminPart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.model.Group;
import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {
    private List<Group> groups;

    public GroupAdapter(List<Group> groups) {
        this.groups = groups;
    }
    public void updateGroups(List<Group> filteredGroups) {
        this.groups = filteredGroups;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_group, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Group group = groups.get(position);
        holder.bind(
                "Group: " + group.getNumeric(),
                "Level: " + group.getLevel().getLvl()
        );
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }

    static class GroupViewHolder extends RecyclerView.ViewHolder {
        private final TextView numericGroupView, levelGroupView;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            numericGroupView = itemView.findViewById(R.id.numericGroupView);
            levelGroupView = itemView.findViewById(R.id.levelGroupView);
        }

        public void bind(String numericGroup, String levelGroup) {
            numericGroupView.setText(numericGroup);
            levelGroupView.setText(levelGroup);
        }
    }
}