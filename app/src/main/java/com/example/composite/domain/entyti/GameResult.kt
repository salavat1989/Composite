package com.example.composite.domain.entyti

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameResult(
    val isWin: Boolean,
    val countOfQuestions: Int,
    val countOfRightAnswers: Int,
    val gameSettings: GameSettings
) : Parcelable