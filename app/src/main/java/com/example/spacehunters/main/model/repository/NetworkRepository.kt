package com.example.spacehunters.main.model.repository

import com.example.spacehunters.main.model.entities.epicphotos.EpicPhoto
import com.example.spacehunters.main.model.entities.picturefrommars.PhotoFromMars
import com.example.spacehunters.main.model.entities.pictureoftheday.AstroPOD
interface NetworkRepository {
    fun getPhotoFromServer(date: String): AstroPOD
    fun getPhotoFromMars(date : String) : ArrayList<PhotoFromMars>
    fun getEpicPhotos(date: String) : ArrayList<EpicPhoto>
}