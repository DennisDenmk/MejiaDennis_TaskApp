package com.example.taskapp.data;

public class TaskCreateRequest {
    private String username;
    private String password;
    private String title;

    public TaskCreateRequest(String username, String password, String title) {
        this.username = username;
        this.password = password;
        this.title = title;
    }

    // Getters and Setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
}
