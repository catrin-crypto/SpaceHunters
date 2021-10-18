package com.example.spacehunters.main.model.entities.epicphotos

import com.example.spacehunters.main.model.entities.ApiUtils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EpicPhotosRepo {
    val epicApi: EpicPhotosApi by lazy {

        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHTTPBuilderWithHeaders())
            .build()

        adapter.create(EpicPhotosApi::class.java)

    }
}