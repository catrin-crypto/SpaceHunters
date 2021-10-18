package com.example.spacehunters.main.model.entities.picturefrommars

import com.example.spacehunters.main.model.entities.AstroPODdto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoFromMarsApi {
    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    fun getPhotoFromMars(
        @Query("api_key") apiKey: String,
        @Query("earth_date") date : String,
        @Query("camera") camera : String
    ) : Call<MarsResponseDTO>
}