package com.example.composite.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composite.domain.entyti.Level
import java.lang.RuntimeException

class GameViewModelFactory(private val application: Application,private val level: Level): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)){
            val viewModel: T = GameViewModel(application, level) as T
            return viewModel
        }

        else
            throw RuntimeException("Unknown viewModel class $modelClass")
    }
}