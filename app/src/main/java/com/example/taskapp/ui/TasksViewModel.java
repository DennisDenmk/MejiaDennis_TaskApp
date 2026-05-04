package com.example.taskapp.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.taskapp.data.ApiResponse;
import com.example.taskapp.data.Task;
import com.example.taskapp.data.TaskRepository;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TasksViewModel extends ViewModel {
    private TaskRepository repository;
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<List<Task>> tasks = new MutableLiveData<>();

    public TasksViewModel(TaskRepository repository) {
        this.repository = repository;
    }

    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<String> getError() { return error; }
    public LiveData<List<Task>> getTasks() { return tasks; }

    public void fetchTasks() {
        Call<List<Task>> call = repository.getTasks();
        if (call == null) return;
        loading.setValue(true);
        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                if (response.isSuccessful()) {
                    tasks.setValue(response.body());
                } else {
                    error.setValue("Failed to fetch tasks: " + response.code());
                }
                loading.setValue(false);
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {
                error.setValue(t.getMessage());
                loading.setValue(false);
            }
        });
    }

    public void createTask(String title) {
        Call<Task> call = repository.createTask(title);
        if (call == null) return;
        loading.setValue(true);
        call.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                if (response.isSuccessful()) {
                    fetchTasks();
                } else {
                    error.setValue("Failed to create task: " + response.code());
                }
                loading.setValue(false);
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                error.setValue(t.getMessage());
                loading.setValue(false);
            }
        });
    }

    public void toggleTask(Task task) {
        Call<Task> call = repository.updateTask(task.getId(), !task.isCompleted());
        if (call == null) return;
        call.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                if (response.isSuccessful()) {
                    fetchTasks();
                } else {
                    error.setValue("Failed to update task: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                error.setValue(t.getMessage());
            }
        });
    }

    public void updateTaskTitle(Task task, String newTitle) {
        Call<Task> call = repository.updateTaskTitle(task.getId(), newTitle);
        if (call == null) return;
        loading.setValue(true);
        call.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                if (response.isSuccessful()) {
                    fetchTasks();
                } else {
                    error.setValue("Failed to update task title: " + response.code());
                }
                loading.setValue(false);
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                error.setValue(t.getMessage());
                loading.setValue(false);
            }
        });
    }

    public void deleteTask(Task task) {
        Call<ApiResponse> call = repository.deleteTask(task.getId());
        if (call == null) return;
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    fetchTasks();
                } else {
                    error.setValue("Failed to delete task: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                error.setValue(t.getMessage());
            }
        });
    }

    public void clearError() {
        error.setValue(null);
    }
}
