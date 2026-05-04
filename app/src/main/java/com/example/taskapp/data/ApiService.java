package com.example.taskapp.data;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @POST("register")
    Call<ApiResponse> register(@Body AuthRequest request);

    @POST("login")
    Call<ApiResponse> login(@Body AuthRequest request);

    @POST("tasks/list")
    Call<List<Task>> getTasks(@Body AuthRequest request);

    @POST("tasks")
    Call<Task> createTask(@Body TaskCreateRequest request);

    @POST("tasks/{id}/update")
    Call<Task> updateTask(@Path("id") int id, @Body TaskUpdateRequest request);

    @POST("tasks/{id}/delete")
    Call<ApiResponse> deleteTask(@Path("id") int id, @Body TaskDeleteRequest request);
}
