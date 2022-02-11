package com.example.composite.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.composite.R
import com.example.composite.data.CompositeRepositoryImpl
import com.example.composite.domain.entyti.GameResult
import com.example.composite.domain.entyti.GameSettings
import com.example.composite.domain.entyti.Level
import com.example.composite.domain.entyti.Question
import com.example.composite.domain.usecase.GenerateQuestionUseCase
import com.example.composite.domain.usecase.GetGameSettingsUseCase
import kotlin.math.max

class GameViewModel(private val application: Application,private val level:Level) : ViewModel() {
    private val repository = CompositeRepositoryImpl
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)
    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private var timer: CountDownTimer? = null
    private var _gameSettings: GameSettings? = null
    val gameSettings
        get() = _gameSettings ?: throw RuntimeException("GameSettings is Null")

    private var _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    private var countOfAnswers = 0
    private var countOfRightAnswers = 0
    private var _leftTimeString = MutableLiveData<String>()
    val leftTimeString: LiveData<String>
        get() = _leftTimeString
    private var _percentOfRightAnswers = MutableLiveData<Int>()
    val percentOfRightAnswers: LiveData<Int>
        get() = _percentOfRightAnswers
    private var _percentOfRightAnswersMin = MutableLiveData<Int>()
    val percentOfRightAnswersMin: LiveData<Int>
        get() = _percentOfRightAnswersMin
    private var _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult
    private var _rightAnswersProgress = MutableLiveData<String>()
    val rightAnswersProgress: LiveData<String>
        get() = _rightAnswersProgress
    private var _enoughRightPercent = MutableLiveData<Boolean>()
    val enoughRightPercent: LiveData<Boolean>
        get() = _enoughRightPercent
    private var _enoughRightCount = MutableLiveData<Boolean>()
    val enoughRightCount: LiveData<Boolean>
        get() = _enoughRightCount

    init{
        _gameSettings = getGameSettingsUseCase(level)
        setDefaultValues()
        getNextQuestion()
        startGameTimer(gameSettings.gameTimeInSeconds)
    }

    private fun setDefaultValues() {
        changeState()
        _percentOfRightAnswersMin.value = gameSettings.minRightAnswersPercent
    }

    private fun getNextQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSum)
    }

    private fun startGameTimer(countDownTimeSeconds: Int) {
        timer = object : CountDownTimer(
            countDownTimeSeconds * MILLISECONDS_IN_SECOND,
            MILLISECONDS_IN_SECOND
        ) {
            override fun onTick(p0: Long) {
                _leftTimeString.value = String.format(
                    application.resources.getString(R.string.time_left),
                    (p0 / MILLISECONDS_IN_SECOND).toString()
                )
            }

            override fun onFinish() {
                gameFinished()
            }
        }
        timer?.start()
    }

    private fun gameFinished() {
        timer?.cancel()
        val isWin = countOfRightAnswers >= gameSettings.minRightAnswersCount
                && calculatePercentOfRightAnswers() >= gameSettings.minRightAnswersPercent
        GameResult(
            isWin,
            countOfAnswers,
            countOfRightAnswers,
            gameSettings
        ).also { _gameResult.value = it }
    }

    fun checkAnswer(answer: Int) {
        if (answer == _question.value?.rightAnswer) {
            countOfRightAnswers++
        }
        countOfAnswers++
        changeState()
        getNextQuestion()
    }

    private fun changeState() {
        _percentOfRightAnswers.value = calculatePercentOfRightAnswers()

        _rightAnswersProgress.value = String.format(
            application.resources.getString(R.string.right_answer_count),
            countOfRightAnswers.toString(),
            gameSettings.minRightAnswersCount.toString()
        )
        _enoughRightPercent.value =
            calculatePercentOfRightAnswers() >= gameSettings.minRightAnswersPercent
        _enoughRightCount.value = countOfRightAnswers >= gameSettings.minRightAnswersCount
    }

    private fun calculatePercentOfRightAnswers(): Int {
        return (100 * countOfRightAnswers.toDouble() / max(countOfAnswers, 1)).toInt()
    }

    companion object {
        private const val MILLISECONDS_IN_SECOND = 1000L
    }
}