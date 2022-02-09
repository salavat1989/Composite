package com.example.composite.domain.usecase

import com.example.composite.domain.repository.CompositeRepository
import com.example.composite.domain.entyti.Question

class GenerateQuestionUseCase(private val repository: CompositeRepository) {
    operator fun invoke(maxSumValue:Int): Question {
        return repository.generateQuestion(maxSumValue,ANSWER_COUNT)
    }
    companion object{
        private const val ANSWER_COUNT = 6
    }
}