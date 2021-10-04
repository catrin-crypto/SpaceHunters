package com.example.spacehunters.main.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacehunters.main.AppState
import com.example.spacehunters.main.model.repository.Repository
import java.text.SimpleDateFormat
import java.util.*

class PhotoFromMarsViewModel(private val repository: Repository) : ViewModel(), LifecycleObserver {
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()


    @RequiresApi(Build.VERSION_CODES.O)
    fun loadData(date: String) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            try{
             val data = repository.getPhotoFromMars(date)
            liveDataToObserve.postValue(AppState.SuccessMars(data))
            }catch (e : Exception){liveDataToObserve.postValue(AppState.Error(e))}
        }.start()
    }
}