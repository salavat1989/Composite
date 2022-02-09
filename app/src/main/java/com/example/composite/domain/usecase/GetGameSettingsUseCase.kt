package com.example.composite.domain.usecase

import com.example.composite.domain.repository.CompositeRepository
import com.example.composite.domain.entyti.GameSettings
import com.example.composite.domain.entyti.Level

class GetGameSettingsUseCase (private val repository: CompositeRepository) {
    operator fun invoke(level: Level):GameSettings{
        return repository.getGameSettings(level)
    }
}