package com.example.spacehunters.main.model.entities.picturefrommars

import com.example.spacehunters.main.model.entities.ApiUtils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PhotoFromMarsRepo {
    val marsApi: PhotoFromMarsApi by lazy {

        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHTTPBuilderWithHeaders())
            .build()

        adapter.create(PhotoFromMarsApi::class.java)

    }
}