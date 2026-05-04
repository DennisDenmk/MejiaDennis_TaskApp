package com.example.taskapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskapp.data.AuthRequest
import com.example.taskapp.data.TaskRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    private val _authSuccess = MutableLiveData<Boolean>()
    val authSuccess: LiveData<Boolean> get() = _authSuccess

    fun login(auth: AuthRequest) {
        _loading.value = true
        repository.saveAuth(auth)
        viewModelScope.launch {
            try {
                val response = repository.getTasks()
                if (response.isSuccessful) {
                    _authSuccess.postValue(true)
                } else {
                    repository.clearAuth()
                    _error.postValue("Login failed: ${response.code()}")
                }
            } catch (e: Exception) {
                repository.clearAuth()
                _error.postValue(e.message ?: "An error occurred")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun register(auth: AuthRequest) {
        _loading.value = true
        viewModelScope.launch {
            try {
                val response = repository.register(auth)
                if (response.isSuccessful) {
                    _error.postValue("Registration successful! Please login.")
                } else {
                    _error.postValue("Registration failed: ${response.code()}")
                }
            } catch (e: Exception) {
                _error.postValue(e.message ?: "An error occurred")
            } finally {
                _loading.postValue(false)
            }
        }
    }

    fun checkLoggedIn() {
        if (repository.getAuth() != null) {
            _authSuccess.value = true
        }
    }
    
    fun logout() {
        repository.clearAuth()
        _authSuccess.value = false
    }

    fun clearError() {
        _error.value = null
    }
}
