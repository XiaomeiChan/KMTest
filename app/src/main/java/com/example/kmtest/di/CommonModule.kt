package com.example.kmtest.di

import com.example.kmtest.repository.UserRepository
import com.example.kmtest.ui.second.SecondViewModel
import com.example.kmtest.ui.third.ThirdViewModel
import org.koin.dsl.module

val repositoryModule = module {
    single { UserRepository(get(), get()) }
}

val viewModule = module {
    single {ThirdViewModel(get())}
    single {SecondViewModel()}
}