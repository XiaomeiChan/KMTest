package com.example.kmtest.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val first_name : String,
    val last_name : String,
    val avatars : String,
)
