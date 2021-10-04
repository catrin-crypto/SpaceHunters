package com.example.spacehunters.main.model.entities.epicphotos

import com.example.spacehunters.main.model.entities.picturefrommars.MarsResponseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpicPhotosApi {
    //https://api.nasa.gov/EPIC/api/natural/date/2021-05-30?api_key=DEMO_KEY
    @GET("EPIC/api/natural/date/{date}")
    fun getEpicPhotos(
        @Path("date") date : String,
        @Query("api_key") apiKey: String,
    ) : Call<EpicPhotoDTOarray>

}