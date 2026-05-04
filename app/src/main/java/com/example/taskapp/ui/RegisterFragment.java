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
import com.example.taskapp.data.AuthRequest;
import com.example.taskapp.databinding.FragmentRegisterBinding;
import com.google.android.material.snackbar.Snackbar;

public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        if (activity == null) return;

        AuthViewModel viewModel = activity.authViewModel;

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Snackbar.make(binding.getRoot(), error, Snackbar.LENGTH_LONG).show();
                viewModel.clearError();
            }
        });

        binding.btnRegister.setOnClickListener(v -> {
            String user = binding.etUsername.getText().toString();
            String pass = binding.etPassword.getText().toString();
            if (!user.isEmpty() && !pass.isEmpty()) {
                viewModel.register(new AuthRequest(user, pass));
            } else {
                Snackbar.make(binding.getRoot(), "Please enter username and password", Snackbar.LENGTH_SHORT).show();
            }
        });

        binding.tvLoginLink.setOnClickListener(v -> 
            Navigation.findNavController(view).popBackStack()
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
