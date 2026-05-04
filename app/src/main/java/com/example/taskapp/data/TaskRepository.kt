package com.example.taskapp.data

import retrofit2.Response

class TaskRepository(private val api: ApiService, private val authManager: AuthManager) {

    suspend fun register(auth: AuthRequest): Response<ApiResponse> = api.register(auth)

    suspend fun getTasks(): Response<List<Task>> {
        val auth = authManager.getCredentials() ?: return Response.error(401, okhttp3.ResponseBody.create(null, "No credentials"))
        return api.getTasks(auth)
    }

    suspend fun createTask(title: String): Response<Task> {
        val auth = authManager.getCredentials() ?: return Response.error(401, okhttp3.ResponseBody.create(null, "No credentials"))
        return api.createTask(TaskCreateRequest(auth.username, auth.password, title))
    }

    suspend fun updateTask(id: Int, completed: Boolean): Response<Task> {
        val auth = authManager.getCredentials() ?: return Response.error(401, okhttp3.ResponseBody.create(null, "No credentials"))
        return api.updateTask(id, TaskUpdateRequest(auth.username, auth.password, completed = completed))
    }

    suspend fun updateTaskTitle(id: Int, title: String): Response<Task> {
        val auth = authManager.getCredentials() ?: return Response.error(401, okhttp3.ResponseBody.create(null, "No credentials"))
        return api.updateTask(id, TaskUpdateRequest(auth.username, auth.password, title = title))
    }

    suspend fun deleteTask(id: Int): Response<ApiResponse> {
        val auth = authManager.getCredentials() ?: return Response.error(401, okhttp3.ResponseBody.create(null, "No credentials"))
        return api.deleteTask(id, TaskDeleteRequest(auth.username, auth.password))
    }
    
    fun saveAuth(auth: AuthRequest) = authManager.saveCredentials(auth)
    fun getAuth() = authManager.getCredentials()
    fun clearAuth() = authManager.clear()
}
