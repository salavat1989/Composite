package com.example.composite.domain.entyti

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings(
    val maxSum: Int,
    val minRightAnswersCount: Int,
    val minRightAnswersPercent: Double,
    val gameTimeInSeconds:Int
): Parcelable