# Quiz App - Android

This project is a simple Android Quiz Application developed as part of my SIT708 Credit Task 3.1 assignment.

## Project Description
This app allows users to attempt a multiple-choice quiz, track their progress, and view their final score. It also includes a dark mode and light mode feature.

## Features
- User enters their name before starting the quiz
- Multiple-choice questions with 4 options
- Submit button to check answers
- Correct answer turns green
- Incorrect selected answer turns red
- Progress bar showing quiz progress
- Result screen displaying final score
- "Take New Quiz" button returns to main screen
- User name is saved and reused
- "Finish" button closes the app
- Dark mode / Light mode toggle

## Technologies Used
- Android Studio
- Kotlin
- XML
- SharedPreferences

## App Structure
- MainActivity.kt → Handles user name input and navigation to quiz
- QuizActivity.kt → Displays questions, checks answers, updates progress
- ResultActivity.kt → Shows final score and options
- Question.kt → Stores question data

## How to Run the App
1. Open the project in Android Studio
2. Let Gradle sync complete
3. Run the app on an emulator or Android device
4. Enter your name and start the quiz

## Author
Hemanth
