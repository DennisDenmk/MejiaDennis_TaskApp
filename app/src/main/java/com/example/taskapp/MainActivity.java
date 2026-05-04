package com.example.taskapp;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.taskapp.data.AuthManager;
import com.example.taskapp.data.RetrofitClient;
import com.example.taskapp.data.TaskRepository;
import com.example.taskapp.databinding.ActivityMainBinding;
import com.example.taskapp.ui.AuthViewModel;
import com.example.taskapp.ui.TasksViewModel;
import com.example.taskapp.ui.ViewModelFactory;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    public AuthViewModel authViewModel;
    public TasksViewModel tasksViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TaskRepository repository = new TaskRepository(RetrofitClient.getInstance(), new AuthManager(this));
        ViewModelFactory factory = new ViewModelFactory(repository);

        authViewModel = new ViewModelProvider(this, factory).get(AuthViewModel.class);
        tasksViewModel = new ViewModelProvider(this, factory).get(TasksViewModel.class);

        authViewModel.getLoading().observe(this, isLoading -> 
            binding.loadingSpinner.setVisibility(isLoading ? View.VISIBLE : View.GONE)
        );

        tasksViewModel.getLoading().observe(this, isLoading -> 
            binding.loadingSpinner.setVisibility(isLoading ? View.VISIBLE : View.GONE)
        );
    }
}
