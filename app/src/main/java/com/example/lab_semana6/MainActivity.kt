package com.example.lab_semana6

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var preferences: SharedPreferences

    private val prefsName = "user_preferences"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        preferences = getSharedPreferences(prefsName, Context.MODE_PRIVATE)

        val etUsername = findViewById<EditText>(R.id.et_username)
        val swDarkMode = findViewById<Switch>(R.id.sw_dark_mode)
        val swRemember = findViewById<Switch>(R.id.sw_remember)
        val tvLaunchCount = findViewById<TextView>(R.id.tv_launch_count)
        val btnSave = findViewById<Button>(R.id.btn_save)
        val btnReset = findViewById<Button>(R.id.btn_reset)

        val currentLaunches = preferences.getInt("launch_count", 0) + 1

        preferences
            .edit()
            .putInt("launch_count", currentLaunches)
            .apply()

        tvLaunchCount.text = "Veces abierta: $currentLaunches"

        etUsername.setText(preferences.getString("username", ""))
        swDarkMode.isChecked = preferences.getBoolean("dark_mode", false)
        swRemember.isChecked = preferences.getBoolean("remember_session", false)

        btnSave.setOnClickListener {
            preferences
                .edit()
                .putString("username", etUsername.text.toString().trim())
                .putBoolean("dark_mode", swDarkMode.isChecked)
                .putBoolean("remember_session", swRemember.isChecked)
                .apply()

            Toast.makeText(
                this,
                "Preferencias guardadas exitosamente",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnReset.setOnClickListener {
            preferences
                .edit()
                .clear()
                .apply()

            etUsername.text.clear()
            swDarkMode.isChecked = false
            swRemember.isChecked = false
            tvLaunchCount.text = "Veces abierta: 0"

            Toast.makeText(
                this,
                "Datos eliminados",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
