package com.example.spacehunters.main.model.entities.pictureoftheday

import com.example.spacehunters.main.model.entities.ApiUtils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PictureOfTheDayRepo {
    val api: PictureOfTheDayApi by lazy {

        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHTTPBuilderWithHeaders())
            .build()

        adapter.create(PictureOfTheDayApi::class.java)

    }
}