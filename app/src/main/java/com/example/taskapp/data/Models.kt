package com.example.taskapp.data

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    val username: String,
    val password: String
)

data class Task(
    val id: Int,
    val title: String,
    val completed: Boolean
)

data class TaskCreateRequest(
    val username: String,
    val password: String,
    val title: String
)

data class TaskUpdateRequest(
    val username: String,
    val password: String,
    val title: String? = null,
    val completed: Boolean? = null
)

data class TaskDeleteRequest(
    val username: String,
    val password: String
)

data class ApiResponse(
    val message: String? = null,
    val error: String? = null
)
