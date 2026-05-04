package com.example.taskapp.data;

import android.content.Context;
import android.content.SharedPreferences;

public class AuthManager {
    private SharedPreferences prefs;

    public AuthManager(Context context) {
        this.prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE);
    }

    public void saveCredentials(AuthRequest auth) {
        prefs.edit()
                .putString("username", auth.getUsername())
                .putString("password", auth.getPassword())
                .apply();
    }

    public AuthRequest getCredentials() {
        String u = prefs.getString("username", null);
        String p = prefs.getString("password", null);
        if (u != null && p != null) {
            return new AuthRequest(u, p);
        }
        return null;
    }

    public void clear() {
        prefs.edit().clear().apply();
    }
}
