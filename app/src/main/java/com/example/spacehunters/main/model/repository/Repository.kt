package com.example.spacehunters.main.model.repository

import com.example.spacehunters.main.model.entities.AstroPOD
interface Repository {
    fun getPhotoFromServer(date: String): AstroPOD
}