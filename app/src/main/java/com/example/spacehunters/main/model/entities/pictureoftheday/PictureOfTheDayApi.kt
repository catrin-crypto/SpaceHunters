package com.example.spacehunters.main.model.entities.pictureoftheday

import com.example.spacehunters.main.model.entities.AstroPODdto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayApi {
    @GET("planetary/apod")
    fun getPictureOfTheDay(
        @Query("api_key") apiKey: String,
        @Query("date") date : String
    ) : Call<AstroPODdto>
}