package com.example.spacehunters.main.model.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.spacehunters.BuildConfig
import com.example.spacehunters.R
import com.example.spacehunters.main.model.entities.AstroPOD
import com.example.spacehunters.main.model.entities.MediaTypes
import com.example.spacehunters.main.model.entities.PictureOfTheDayRepo
import java.io.File
import java.io.FileInputStream
import java.util.*

class RepositoryImpl : Repository{
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getPhotoFromServer(date : String): AstroPOD {
        val dto = PictureOfTheDayRepo.api.getPictureOfTheDay(BuildConfig.NASA_API_KEY,date)
            .execute()
            .body()

        dto?.let{
            return AstroPOD(
                description = it.description,
                date = it.date,
                title = it.title,
                image = if (it.mediaType == MediaTypes.IMAGE.type) it.image else null
            )
        }
        return AstroPOD()
    }

}