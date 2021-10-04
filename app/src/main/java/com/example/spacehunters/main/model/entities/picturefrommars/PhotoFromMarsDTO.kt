package com.example.spacehunters.main.model.entities.picturefrommars

import com.google.gson.annotations.SerializedName

data class PhotoFromMarsDTO(
    @SerializedName("img_src") val imgSrc: String,
    val id: Int
)

data class MarsResponseDTO(
    val photos : ArrayList<PhotoFromMarsDTO>
)