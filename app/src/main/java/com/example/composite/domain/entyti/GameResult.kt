package com.example.composite.domain.entyti

data class GameResult(
    val isWin: Boolean,
    val countOfQuestions: Int,
    val counOfRightAnswers: Int,
    val gameSettings: GameSettings
)