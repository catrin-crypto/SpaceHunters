package com.example.spacehunters.main.model.entities.picturefrommars

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PhotoFromMars (
    val imgSrc : String,
    val id : Int
        ) : Parcelable