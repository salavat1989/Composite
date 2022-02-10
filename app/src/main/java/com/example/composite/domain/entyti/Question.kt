package com.example.composite.domain.entyti

data class Question(
    val sum: Int,
    val visibleNumber: Int,
    val answers: List<Int>
) {
    val rightAnswer = sum - visibleNumber
}