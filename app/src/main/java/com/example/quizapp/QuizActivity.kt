package com.example.quizapp

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class QuizActivity : AppCompatActivity() {

    private lateinit var tvQuestion: TextView
    private lateinit var btnOption1: Button
    private lateinit var btnOption2: Button
    private lateinit var btnOption3: Button
    private lateinit var btnOption4: Button
    private lateinit var btnSubmit: Button
    private lateinit var btnNext: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var tvProgress: TextView
    private lateinit var switchTheme: Switch
    private lateinit var sharedPreferences: SharedPreferences

    private var currentQuestionIndex = 0
    private var selectedAnswerIndex = -1
    private var score = 0
    private var answerSubmitted = false

    private val questionList = listOf(
        Question(
            "What is Android?",
            listOf("Operating System", "Web Browser", "Database", "Laptop"),
            0
        ),
        Question(
            "Which language is commonly used for Android development?",
            listOf("Python", "Kotlin", "PHP", "C"),
            1
        ),
        Question(
            "Which file is used for Android app layout design?",
            listOf("Main.kt", "activity_main.xml", "Android.java", "values.txt"),
            1
        ),
        Question(
            "What does UI stand for?",
            listOf("User Interface", "Universal Internet", "User Input", "Unique Index"),
            0
        ),
        Question(
            "Which component is used to move between screens?",
            listOf("Intent", "Toast", "Button", "ImageView"),
            0
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("QuizAppPrefs", MODE_PRIVATE)

        applySavedTheme()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        tvQuestion = findViewById(R.id.tvQuestion)
        btnOption1 = findViewById(R.id.btnOption1)
        btnOption2 = findViewById(R.id.btnOption2)
        btnOption3 = findViewById(R.id.btnOption3)
        btnOption4 = findViewById(R.id.btnOption4)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnNext = findViewById(R.id.btnNext)
        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tvProgress)
        switchTheme = findViewById(R.id.switchThemeQuiz)

        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)
        switchTheme.isChecked = isDarkMode

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            saveTheme(isChecked)
        }

        progressBar.max = questionList.size

        loadQuestion()

        btnOption1.setOnClickListener { selectOption(0) }
        btnOption2.setOnClickListener { selectOption(1) }
        btnOption3.setOnClickListener { selectOption(2) }
        btnOption4.setOnClickListener { selectOption(3) }

        btnSubmit.setOnClickListener {
            if (!answerSubmitted && selectedAnswerIndex != -1) {
                checkAnswer()
            }
        }

        btnNext.setOnClickListener {
            currentQuestionIndex++

            if (currentQuestionIndex < questionList.size) {
                loadQuestion()
            } else {
                val userName = intent.getStringExtra("USER_NAME") ?: ""
                val resultIntent = Intent(this, ResultActivity::class.java)
                resultIntent.putExtra("USER_NAME", userName)
                resultIntent.putExtra("FINAL_SCORE", score)
                resultIntent.putExtra("TOTAL_QUESTIONS", questionList.size)
                startActivity(resultIntent)
                finish()
            }
        }
    }

    private fun loadQuestion() {
        val currentQuestion = questionList[currentQuestionIndex]

        tvQuestion.text = currentQuestion.questionText
        btnOption1.text = currentQuestion.options[0]
        btnOption2.text = currentQuestion.options[1]
        btnOption3.text = currentQuestion.options[2]
        btnOption4.text = currentQuestion.options[3]

        resetOptionButtons()

        selectedAnswerIndex = -1
        answerSubmitted = false
        btnNext.isEnabled = false

        progressBar.progress = currentQuestionIndex
        tvProgress.text = "Question ${currentQuestionIndex + 1} of ${questionList.size}"
    }

    private fun selectOption(index: Int) {
        if (answerSubmitted) return

        selectedAnswerIndex = index
        resetOptionButtons()

        when (index) {
            0 -> btnOption1.setBackgroundColor(Color.LTGRAY)
            1 -> btnOption2.setBackgroundColor(Color.LTGRAY)
            2 -> btnOption3.setBackgroundColor(Color.LTGRAY)
            3 -> btnOption4.setBackgroundColor(Color.LTGRAY)
        }
    }

    private fun checkAnswer() {
        answerSubmitted = true
        val currentQuestion = questionList[currentQuestionIndex]
        val correctIndex = currentQuestion.correctAnswerIndex

        if (selectedAnswerIndex == correctIndex) {
            score++
        }

        if (selectedAnswerIndex != correctIndex) {
            getButtonByIndex(selectedAnswerIndex).setBackgroundColor(Color.RED)
        }

        getButtonByIndex(correctIndex).setBackgroundColor(Color.GREEN)

        disableOptionButtons()
        btnNext.isEnabled = true
        progressBar.progress = currentQuestionIndex + 1
    }

    private fun getButtonByIndex(index: Int): Button {
        return when (index) {
            0 -> btnOption1
            1 -> btnOption2
            2 -> btnOption3
            else -> btnOption4
        }
    }

    private fun resetOptionButtons() {
        val defaultColor = "#6200EE"

        btnOption1.setBackgroundColor(Color.parseColor(defaultColor))
        btnOption2.setBackgroundColor(Color.parseColor(defaultColor))
        btnOption3.setBackgroundColor(Color.parseColor(defaultColor))
        btnOption4.setBackgroundColor(Color.parseColor(defaultColor))

        enableOptionButtons()
    }

    private fun disableOptionButtons() {
        btnOption1.isEnabled = false
        btnOption2.isEnabled = false
        btnOption3.isEnabled = false
        btnOption4.isEnabled = false
    }

    private fun enableOptionButtons() {
        btnOption1.isEnabled = true
        btnOption2.isEnabled = true
        btnOption3.isEnabled = true
        btnOption4.isEnabled = true
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