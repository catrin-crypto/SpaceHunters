package com.example.spacehunters.main.model.entities

import android.content.Context
import android.content.res.Resources
import android.os.Parcelable
import android.provider.Settings.Global.getString
import com.example.spacehunters.R
import com.example.spacehunters.main.App
import kotlinx.parcelize.Parcelize

@Parcelize
data class AstroPOD(
    val title : String = App.Companion.appContext.getString(R.string.photo_of_the_day),
    val image : String ? = null,
    val description : String = App.Companion.appContext.getString(R.string.no_description),
    val date : String ? = null
): Parcelable
