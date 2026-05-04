package com.example.taskapp.data;

public class TaskUpdateRequest {
    private String username;
    private String password;
    private String title;
    private Boolean completed;

    public TaskUpdateRequest(String username, String password, String title, Boolean completed) {
        this.username = username;
        this.password = password;
        this.title = title;
        this.completed = completed;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Boolean getCompleted() { return completed; }
    public void setCompleted(Boolean completed) { this.completed = completed; }
}
