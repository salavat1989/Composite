package com.example.composite.data

import com.example.composite.domain.repository.CompositeRepository
import com.example.composite.domain.entyti.GameSettings
import com.example.composite.domain.entyti.Level
import com.example.composite.domain.entyti.Question
import kotlin.random.Random
import kotlin.math.max
import kotlin.math.min

object CompositeRepositoryImpl: CompositeRepository {
    private const val MIN_SUM_VALUE = 3
    private const val MIN_NUMBER_VALUE = 1
    override fun generateQuestion(maxSumValue: Int, answersCount: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE,maxSumValue+1)
        val visibleNumber = Random.nextInt(MIN_NUMBER_VALUE,sum)
        val optionsFrom = max(MIN_NUMBER_VALUE,visibleNumber - answersCount)
        val optionsTo = min(maxSumValue,-visibleNumber + answersCount)
        val options = HashSet<Int>()
        options.add(visibleNumber)
        while(options.size<answersCount){
            options.add( Random.nextInt(optionsFrom,optionsTo))
        }
        return Question(sum,visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when(level){
            Level.TEST -> GameSettings(10,5, 50.0,10)
            Level.EASY -> GameSettings(10,10, 50.0,60)
            Level.MEDIUM -> GameSettings(20,20, 70.0,50)
            Level.HARD -> GameSettings(30,30, 90.0,40)
        }
    }
}