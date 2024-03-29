package com.example.spacehunters.main.model.entities

import com.example.spacehunters.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

object ApiUtils {
    private val baseUrlMainPart = "https://api.nasa.gov/"
    val baseUrl = baseUrlMainPart

    fun getOkHTTPBuilderWithHeaders(): OkHttpClient {
            val httpClient = OkHttpClient.Builder()

        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.readTimeout(10, TimeUnit.SECONDS)
        httpClient.writeTimeout(10, TimeUnit.SECONDS)
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("api_key", BuildConfig.NASA_API_KEY)
                .method(original.method(), original.body())
                .build()
           // try {
                chain.proceed(request)
           // }catch(ioex: IOException){}
        }
            return httpClient.build()
    }
}