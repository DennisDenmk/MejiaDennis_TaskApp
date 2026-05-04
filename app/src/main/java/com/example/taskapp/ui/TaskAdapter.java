package com.example.taskapp.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.taskapp.data.Task;
import com.example.taskapp.databinding.ItemTaskBinding;

public class TaskAdapter extends ListAdapter<Task, TaskAdapter.TaskViewHolder> {
    private final OnTaskActionListener listener;

    public interface OnTaskActionListener {
        void onToggle(Task task);
        void onEdit(Task task);
        void onDelete(Task task);
    }

    public TaskAdapter(OnTaskActionListener listener) {
        super(new TaskDiffCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTaskBinding binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TaskViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private final ItemTaskBinding binding;

        public TaskViewHolder(ItemTaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Task task) {
            binding.tvTitle.setText(task.getTitle());
            binding.cbCompleted.setChecked(task.isCompleted());

            binding.cbCompleted.setOnClickListener(v -> listener.onToggle(task));
            binding.btnEdit.setOnClickListener(v -> listener.onEdit(task));
            binding.btnDelete.setOnClickListener(v -> listener.onDelete(task));
        }
    }

    static class TaskDiffCallback extends DiffUtil.ItemCallback<Task> {
        @Override
        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getId() == newItem.getId() && 
                   oldItem.getTitle().equals(newItem.getTitle()) && 
                   oldItem.isCompleted() == newItem.isCompleted();
        }
    }
}
