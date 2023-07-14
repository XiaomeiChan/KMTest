package com.example.kmtest

import android.app.Application
import com.example.kmtest.di.localModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import com.example.kmtest.di.networkModule
import com.example.kmtest.di.repositoryModule
import com.example.kmtest.di.viewModule

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin{
            androidContext(this@MyApplication)
            modules (
                networkModule, viewModule, repositoryModule, localModule
            )
        }
    }
}