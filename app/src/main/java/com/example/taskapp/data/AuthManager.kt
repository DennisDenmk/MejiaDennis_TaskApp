package com.example.taskapp.data

import android.content.Context
import android.content.SharedPreferences

class AuthManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveCredentials(auth: AuthRequest) {
        prefs.edit().apply {
            putString("username", auth.username)
            putString("password", auth.password)
            apply()
        }
    }

    fun getCredentials(): AuthRequest? {
        val u = prefs.getString("username", null)
        val p = prefs.getString("password", null)
        return if (u != null && p != null) AuthRequest(u, p) else null
    }

    fun clear() {
        prefs.edit().clear().apply()
    }
}
