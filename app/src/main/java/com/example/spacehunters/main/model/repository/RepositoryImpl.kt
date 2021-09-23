package com.example.spacehunters.main.model.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.spacehunters.R
import com.example.spacehunters.main.model.entities.AstroPOD
import com.example.spacehunters.main.model.entities.MediaTypes
import com.example.spacehunters.main.model.entities.PictureOfTheDayRepo

class RepositoryImpl : Repository{
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getPhotoFromServer(date : String): AstroPOD {
        val dto = PictureOfTheDayRepo.api.getPictureOfTheDay("dqOwzkp1Utb6rCBM1Y9Ov9QrQ5JBIW8foOuvWRgb",date)
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