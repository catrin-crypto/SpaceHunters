package com.example.spacehunters.main.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacehunters.main.AppState
import com.example.spacehunters.main.model.repository.Repository
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class MainViewModel(private val repository: Repository) : ViewModel(), LifecycleObserver {
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()

    fun toSimpleString(date: Date?) = with(date ?: Date()) {
        SimpleDateFormat("yyyy-MM-dd").format(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadData(daysAgo:Int = 0) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            val date = Calendar.getInstance().run {
                add(Calendar.DAY_OF_YEAR, -daysAgo)
                time
            }
            val data = repository.getPhotoFromServer(toSimpleString(date))
            liveDataToObserve.postValue(AppState.Success(data))
        }.start()
    }
}