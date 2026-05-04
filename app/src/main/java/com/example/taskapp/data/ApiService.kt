package com.example.taskapp.data

import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("register")
    suspend fun register(@Body request: AuthRequest): Response<ApiResponse>

    @POST("login")
    suspend fun login(@Body request: AuthRequest): Response<ApiResponse>

    @POST("tasks/list")
    suspend fun getTasks(@Body request: AuthRequest): Response<List<Task>>

    @POST("tasks")
    suspend fun createTask(@Body request: TaskCreateRequest): Response<Task>

    @POST("tasks/{id}/update")
    suspend fun updateTask(@Path("id") id: Int, @Body request: TaskUpdateRequest): Response<Task>

    @POST("tasks/{id}/delete")
    suspend fun deleteTask(@Path("id") id: Int, @Body request: TaskDeleteRequest): Response<ApiResponse>
}