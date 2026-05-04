package com.example.taskapp.data;

import java.util.List;
import retrofit2.Call;

public class TaskRepository {
    private final ApiService api;
    private final AuthManager authManager;

    public TaskRepository(ApiService api, AuthManager authManager) {
        this.api = api;
        this.authManager = authManager;
    }

    public Call<ApiResponse> register(AuthRequest auth) {
        return api.register(auth);
    }

    public Call<ApiResponse> login(AuthRequest auth) {
        return api.login(auth);
    }

    public Call<List<Task>> getTasks() {
        AuthRequest auth = authManager.getCredentials();
        if (auth == null) return null;
        return api.getTasks(auth);
    }

    public Call<Task> createTask(String title) {
        AuthRequest auth = authManager.getCredentials();
        if (auth == null) return null;
        return api.createTask(new TaskCreateRequest(auth.getUsername(), auth.getPassword(), title));
    }

    public Call<Task> updateTask(int id, boolean completed) {
        AuthRequest auth = authManager.getCredentials();
        if (auth == null) return null;
        return api.updateTask(id, new TaskUpdateRequest(auth.getUsername(), auth.getPassword(), null, completed));
    }

    public Call<Task> updateTaskTitle(int id, String title) {
        AuthRequest auth = authManager.getCredentials();
        if (auth == null) return null;
        return api.updateTask(id, new TaskUpdateRequest(auth.getUsername(), auth.getPassword(), title, null));
    }

    public Call<ApiResponse> deleteTask(int id) {
        AuthRequest auth = authManager.getCredentials();
        if (auth == null) return null;
        return api.deleteTask(id, new TaskDeleteRequest(auth.getUsername(), auth.getPassword()));
    }

    public void saveAuth(AuthRequest auth) { authManager.saveCredentials(auth); }
    public AuthRequest getAuth() { return authManager.getCredentials(); }
    public void clearAuth() { authManager.clear(); }
}
