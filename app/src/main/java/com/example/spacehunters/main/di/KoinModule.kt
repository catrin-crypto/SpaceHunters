package com.example.spacehunters.main.di

import com.example.spacehunters.main.model.repository.NetworkRepository
import com.example.spacehunters.main.model.repository.RepositoryImpl
import com.example.spacehunters.main.ui.EpicPhotosViewModel
import com.example.spacehunters.main.ui.MainViewModel
import com.example.spacehunters.main.ui.PhotoFromMarsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<NetworkRepository> { RepositoryImpl() }

    //View models
    viewModel { MainViewModel(get()) }
    viewModel { PhotoFromMarsViewModel(get()) }
    viewModel { EpicPhotosViewModel(get()) }
}