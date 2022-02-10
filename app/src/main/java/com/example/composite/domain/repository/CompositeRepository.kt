package com.example.composite.domain.repository

import com.example.composite.domain.entyti.GameSettings
import com.example.composite.domain.entyti.Level
import com.example.composite.domain.entyti.Question

interface CompositeRepository {
    fun generateQuestion(maxSumValue: Int, answersCount: Int): Question
    fun getGameSettings(level: Level): GameSettings
}