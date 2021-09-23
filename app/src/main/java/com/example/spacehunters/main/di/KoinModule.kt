package com.example.spacehunters.main.di

import com.example.spacehunters.main.model.repository.Repository
import com.example.spacehunters.main.model.repository.RepositoryImpl
import com.example.spacehunters.main.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Repository> { RepositoryImpl() }

    //View models
    viewModel { MainViewModel(get()) }

}