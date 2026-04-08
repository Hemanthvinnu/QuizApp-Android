package com.example.quizapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class ResultActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var btnNewQuiz: Button
    private lateinit var btnFinish: Button
    private lateinit var switchTheme: Switch
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("QuizAppPrefs", MODE_PRIVATE)

        applySavedTheme()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        tvResult = findViewById(R.id.tvResult)
        btnNewQuiz = findViewById(R.id.btnNewQuiz)
        btnFinish = findViewById(R.id.btnFinish)
        switchTheme = findViewById(R.id.switchThemeResult)

        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        switchTheme.isChecked = isDarkMode

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            saveTheme(isChecked)
        }

        val userName = intent.getStringExtra("USER_NAME") ?: "User"
        val finalScore = intent.getIntExtra("FINAL_SCORE", 0)
        val totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 0)

        tvResult.text = "Congratulations $userName!\nYour score is $finalScore / $totalQuestions"

        btnNewQuiz.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnFinish.setOnClickListener {
            finishAffinity()
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