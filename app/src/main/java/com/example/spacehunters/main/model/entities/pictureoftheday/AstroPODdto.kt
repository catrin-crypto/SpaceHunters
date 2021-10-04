package com.example.spacehunters.main.model.entities

import com.google.gson.annotations.SerializedName

enum class MediaTypes(val type : String ) {
    VIDEO("video"),
    IMAGE("image")
}

data class AstroPODdto(
                       @SerializedName("explanation") val description: String,
                       val title: String,
                       val date : String,
                       @SerializedName("media_type") val mediaType : String,
                       @SerializedName("url") val image: String?,
                      )
