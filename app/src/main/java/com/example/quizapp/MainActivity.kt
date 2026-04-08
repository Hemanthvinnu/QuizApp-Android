package com.example.quizapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat

class MainActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var btnStart: Button
    private lateinit var switchTheme: SwitchCompat
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("QuizAppPrefs", MODE_PRIVATE)
        applySavedTheme()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName = findViewById(R.id.etName)
        btnStart = findViewById(R.id.btnStart)
        switchTheme = findViewById(R.id.switchTheme)

        val savedName = sharedPreferences.getString("user_name", "")
        etName.setText(savedName)

        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        switchTheme.isChecked = isDarkMode

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            saveTheme(isChecked)
        }

        btnStart.setOnClickListener {
            val name = etName.text.toString().trim()
            sharedPreferences.edit().putString("user_name", name).apply()

            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("USER_NAME", name)
            startActivity(intent)
        }
    }

    private fun saveTheme(isDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean("dark_mode", isDarkMode).apply()

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        recreate()
    }

    private fun applySavedTheme() {
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}