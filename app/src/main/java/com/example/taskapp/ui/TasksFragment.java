package com.example.taskapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.taskapp.MainActivity;
import com.example.taskapp.R;
import com.example.taskapp.data.Task;
import com.example.taskapp.databinding.FragmentTasksBinding;
import com.google.android.material.snackbar.Snackbar;

public class TasksFragment extends Fragment {
    private FragmentTasksBinding binding;
    private TaskAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTasksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        if (activity == null) return;

        TasksViewModel viewModel = activity.tasksViewModel;
        AuthViewModel authViewModel = activity.authViewModel;

        adapter = new TaskAdapter(new TaskAdapter.OnTaskActionListener() {
            @Override
            public void onToggle(Task task) { viewModel.toggleTask(task); }
            @Override
            public void onEdit(Task task) { showEditTaskDialog(viewModel, task); }
            @Override
            public void onDelete(Task task) { viewModel.deleteTask(task); }
        });

        binding.rvTasks.setAdapter(adapter);

        viewModel.getTasks().observe(getViewLifecycleOwner(), tasks -> adapter.submitList(tasks));

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Snackbar.make(binding.getRoot(), error, Snackbar.LENGTH_LONG).show();
                viewModel.clearError();
            }
        });

        binding.fabAddTask.setOnClickListener(v -> showAddTaskDialog(viewModel));

        binding.btnLogout.setOnClickListener(v -> authViewModel.logout());

        authViewModel.getAuthSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success != null && !success) {
                Navigation.findNavController(view).navigate(R.id.action_tasksFragment_to_loginFragment);
            }
        });

        viewModel.fetchTasks();
    }

    private void showAddTaskDialog(TasksViewModel viewModel) {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_task, null);
        EditText etTitle = dialogView.findViewById(R.id.et_task_title);

        new AlertDialog.Builder(requireContext())
                .setTitle("Add New Task")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String title = etTitle.getText().toString();
                    if (!title.isEmpty()) {
                        viewModel.createTask(title);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showEditTaskDialog(TasksViewModel viewModel, Task task) {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_task, null);
        EditText etTitle = dialogView.findViewById(R.id.et_task_title);
        etTitle.setText(task.getTitle());

        new AlertDialog.Builder(requireContext())
                .setTitle("Edit Task")
                .setView(dialogView)
                .setPositiveButton("Update", (dialog, which) -> {
                    String title = etTitle.getText().toString();
                    if (!title.isEmpty()) {
                        viewModel.updateTaskTitle(task, title);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
