package com.example.spacehunters.main

import com.example.spacehunters.main.model.entities.AstroPOD

sealed class AppState{
    data class Success(val photoOfTheDay: AstroPOD) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
