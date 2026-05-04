package com.example.taskapp.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.taskapp.data.ApiResponse;
import com.example.taskapp.data.AuthRequest;
import com.example.taskapp.data.TaskRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {
    private final TaskRepository repository;
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> authSuccess = new MutableLiveData<>();

    public AuthViewModel(TaskRepository repository) {
        this.repository = repository;
    }

    public LiveData<Boolean> getLoading() { return loading; }
    public LiveData<String> getError() { return error; }
    public LiveData<Boolean> getAuthSuccess() { return authSuccess; }

    public void login(AuthRequest auth) {
        loading.setValue(true);
        repository.login(auth).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    repository.saveAuth(auth);
                    authSuccess.setValue(true);
                } else {
                    repository.clearAuth();
                    String msg = "Login failed: " + response.code();
                    try {
                        if (response.errorBody() != null) {
                            msg += " - " + response.errorBody().string();
                        }
                    } catch (Exception ignored) {}
                    error.setValue(msg);
                }
                loading.setValue(false);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                repository.clearAuth();
                error.setValue(t.getMessage());
                loading.setValue(false);
            }
        });
    }

    public void register(AuthRequest auth) {
        loading.setValue(true);
        repository.register(auth).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    error.setValue("Registration successful! Please login.");
                } else {
                    String msg = "Registration failed: " + response.code();
                    try {
                        if (response.errorBody() != null) {
                            msg += " - " + response.errorBody().string();
                        }
                    } catch (Exception ignored) {}
                    error.setValue(msg);
                }
                loading.setValue(false);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                error.setValue(t.getMessage());
                loading.setValue(false);
            }
        });
    }

    public void checkLoggedIn() {
        if (repository.getAuth() != null) {
            authSuccess.setValue(true);
        }
    }

    public void logout() {
        repository.clearAuth();
        authSuccess.setValue(false);
    }

    public void clearError() {
        error.setValue(null);
    }
}
