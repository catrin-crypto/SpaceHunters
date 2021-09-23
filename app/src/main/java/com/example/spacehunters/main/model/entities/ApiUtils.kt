package com.example.spacehunters.main.model.entities

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object ApiUtils {
    private val baseUrlMainPart = "https://api.nasa.gov/"
    //private val baseUrlCategory = "planetary/"
    val baseUrl = baseUrlMainPart

    fun getOkHTTPBuilderWithHeaders(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.readTimeout(10, TimeUnit.SECONDS)
        httpClient.writeTimeout(10, TimeUnit.SECONDS)
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("api_key", "dqOwzkp1Utb6rCBM1Y9Ov9QrQ5JBIW8foOuvWRgb")
                .method(original.method(), original.body())
                .build()

            chain.proceed(request)
        }

        return httpClient.build()
    }
}