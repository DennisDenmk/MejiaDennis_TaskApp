package com.example.taskapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.example.taskapp.MainActivity;
import com.example.taskapp.R;
import com.example.taskapp.data.AuthRequest;
import com.example.taskapp.databinding.FragmentLoginBinding;
import com.google.android.material.snackbar.Snackbar;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        if (activity == null) return;

        AuthViewModel viewModel = activity.authViewModel;

        viewModel.checkLoggedIn();

        viewModel.getAuthSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success != null && success) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_tasksFragment);
            }
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Snackbar.make(binding.getRoot(), error, Snackbar.LENGTH_LONG).show();
                viewModel.clearError();
            }
        });

        binding.btnLogin.setOnClickListener(v -> {
            String user = binding.etUsername.getText().toString();
            String pass = binding.etPassword.getText().toString();
            if (!user.isEmpty() && !pass.isEmpty()) {
                viewModel.login(new AuthRequest(user, pass));
            } else {
                Snackbar.make(binding.getRoot(), "Please enter username and password", Snackbar.LENGTH_SHORT).show();
            }
        });

        binding.tvRegisterLink.setOnClickListener(v -> 
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
