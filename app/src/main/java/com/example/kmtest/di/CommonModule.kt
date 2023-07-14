package com.example.kmtest.di

import com.example.kmtest.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { UserRepository(get(), get()) }
}

val viewModule = module {
    single {}
}