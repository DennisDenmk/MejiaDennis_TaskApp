package com.example.taskapp.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.taskapp.data.TaskRepository;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final TaskRepository repository;

    public ViewModelFactory(TaskRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AuthViewModel.class)) {
            return (T) new AuthViewModel(repository);
        }
        if (modelClass.isAssignableFrom(TasksViewModel.class)) {
            return (T) new TasksViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
