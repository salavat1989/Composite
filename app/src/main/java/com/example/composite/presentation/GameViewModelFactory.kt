package com.example.composite.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composite.domain.entyti.Level
import java.lang.RuntimeException

class GameViewModelFactory(private val application: Application, private val level: Level) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return modelClass.getConstructor(Application::class.java,Level::class.java).newInstance(application, level) as T
        }
        throw RuntimeException("Unknown viewModel class $modelClass")
    }
}