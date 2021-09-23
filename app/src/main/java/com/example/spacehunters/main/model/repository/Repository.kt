package com.example.spacehunters.main.model.repository

import com.example.spacehunters.main.model.entities.AstroPOD
//TODO
interface Repository {
    fun getPhotoFromServer(date: String): AstroPOD
}