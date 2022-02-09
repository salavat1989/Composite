package com.example.composite.domain.entyti

data class GameSettings(
    val maxSum: Int,
    val minRightAnswersCount: Int,
    val minRightAnswersPercent: Double,
    val gameTimeInSeconds:Int
)