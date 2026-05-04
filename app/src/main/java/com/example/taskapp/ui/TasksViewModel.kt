package com.example.taskapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskapp.data.Task
import com.example.taskapp.data.TaskRepository
import kotlinx.coroutines.launch

class TasksViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> get() = _tasks

    fun fetchTasks() {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.getTasks()
                if (response.isSuccessful) {
                    _tasks.postValue(response.body() ?: emptyList())
                } else {
                    _error.postValue("Failed to fetch tasks: ${response.code()}")
                }
            } catch (e: Exception) {
                _error.postValue(e.message ?: "An error occurred")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun createTask(title: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.createTask(title)
                if (response.isSuccessful) {
                    fetchTasks()
                } else {
                    _error.postValue("Failed to create task: ${response.code()}")
                }
            } catch (e: Exception) {
                _error.postValue(e.message ?: "An error occurred")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun toggleTask(task: Task) {
        viewModelScope.launch {
            try {
                val response = repository.updateTask(task.id, !task.completed)
                if (response.isSuccessful) {
                    fetchTasks()
                } else {
                    _error.postValue("Failed to update task: ${response.code()}")
                }
            } catch (e: Exception) {
                _error.postValue(e.message ?: "An error occurred")
            }
        }
    }

    fun updateTaskTitle(task: Task, newTitle: String) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.updateTaskTitle(task.id, newTitle)
                if (response.isSuccessful) {
                    fetchTasks()
                } else {
                    _error.postValue("Failed to update task title: ${response.code()}")
                }
            } catch (e: Exception) {
                _error.postValue(e.message ?: "An error occurred")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            try {
                val response = repository.deleteTask(task.id)
                if (response.isSuccessful) {
                    fetchTasks()
                } else {
                    _error.postValue("Failed to delete task: ${response.code()}")
                }
            } catch (e: Exception) {
                _error.postValue(e.message ?: "An error occurred")
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
