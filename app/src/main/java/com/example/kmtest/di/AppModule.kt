package com.example.kmtest.di

import androidx.room.Room
import com.example.kmtest.BuildConfig
import com.example.kmtest.database.UserDatabase
import com.example.kmtest.network.ApiService
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val localModule = module {
    factory { get<UserDatabase>().userDao() }
    single {
        Room.databaseBuilder(
            androidContext().applicationContext,
            UserDatabase::class.java, "App.db"
        ).build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder().addInterceptor { chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .build()
            chain.proceed(requestHeaders)
        }
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}