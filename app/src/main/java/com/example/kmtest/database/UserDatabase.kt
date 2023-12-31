package com.example.kmtest.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}