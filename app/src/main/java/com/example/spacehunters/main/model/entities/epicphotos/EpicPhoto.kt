package com.example.spacehunters.main.model.entities.epicphotos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EpicPhoto(
    val caption : String,
    val imageUrl : String
) : Parcelable
