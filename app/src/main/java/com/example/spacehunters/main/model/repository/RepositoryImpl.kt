package com.example.spacehunters.main.model.repository

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.spacehunters.BuildConfig
import com.example.spacehunters.main.model.entities.pictureoftheday.AstroPOD
import com.example.spacehunters.main.model.entities.MediaTypes
import com.example.spacehunters.main.model.entities.epicphotos.EpicPhoto
import com.example.spacehunters.main.model.entities.epicphotos.EpicPhotosRepo
import com.example.spacehunters.main.model.entities.picturefrommars.PhotoFromMars
import com.example.spacehunters.main.model.entities.picturefrommars.PhotoFromMarsRepo
import com.example.spacehunters.main.model.entities.pictureoftheday.PictureOfTheDayRepo
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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

    override fun getPhotoFromMars(date: String): ArrayList<PhotoFromMars> {
        val dto = PhotoFromMarsRepo.marsApi.getPhotoFromMars(BuildConfig.NASA_API_KEY, date,"fhaz")
            .execute()
            .body()
        var marsArr = ArrayList<PhotoFromMars>()

        dto?.let{

            for (photo in it.photos){
                marsArr.add(PhotoFromMars(imgSrc = photo.imgSrc, id = photo.id))
            }
        }
        return marsArr
    }

    @SuppressLint("SimpleDateFormat")
    override fun getEpicPhotos(date: String): ArrayList<EpicPhoto> {
        val dto = EpicPhotosRepo.epicApi.getEpicPhotos(BuildConfig.NASA_API_KEY, date)
            .execute()
            .body()
        var epicArr = ArrayList<EpicPhoto>()
        var chosenDate = Date(date)
        dto?.let {
            for (photo in it.photos){
                epicArr.add(EpicPhoto(caption = photo.caption,
                    imageUrl = "https://api.nasa.gov/EPIC/archive/natural/"+
                            SimpleDateFormat("yyyy/MM/dd/").format(chosenDate)+
                            "png/" + photo.image + ".png?api_key=" + BuildConfig.NASA_API_KEY
                            ))
            }
        }
        return epicArr
    }


}