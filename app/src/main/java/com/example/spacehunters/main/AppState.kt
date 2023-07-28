package com.example.spacehunters.main

import com.example.spacehunters.main.model.entities.epicphotos.EpicPhoto
import com.example.spacehunters.main.model.entities.picturefrommars.PhotoFromMars
import com.example.spacehunters.main.model.entities.pictureoftheday.AstroPOD

sealed class AppState{
    data class Success(val photoOfTheDay: AstroPOD) : AppState()
    data class SuccessMars(val photos: ArrayList<PhotoFromMars>): AppState()
    data class SuccessEpic(val photos : ArrayList<EpicPhoto>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
