package com.example.spacehunters.main.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AstroPOD(
    val title : String = "Picture of the day",
    val image : String ? = null,
    val description : String = "No description",
    val date : String ? = null
): Parcelable
