package com.example.taskapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.taskapp.data.AuthManager
import com.example.taskapp.data.RetrofitClient
import com.example.taskapp.data.TaskRepository
import com.example.taskapp.databinding.ActivityMainBinding
import com.example.taskapp.ui.AuthViewModel
import com.example.taskapp.ui.TasksViewModel
import com.example.taskapp.ui.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var authViewModel: AuthViewModel
    lateinit var tasksViewModel: TasksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = TaskRepository(RetrofitClient.instance, AuthManager(this))
        val factory = ViewModelFactory(repository)

        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
        tasksViewModel = ViewModelProvider(this, factory)[TasksViewModel::class.java]

        authViewModel.loading.observe(this) { isLoading ->
            binding.loadingSpinner.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        
        tasksViewModel.loading.observe(this) { isLoading ->
            binding.loadingSpinner.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}
