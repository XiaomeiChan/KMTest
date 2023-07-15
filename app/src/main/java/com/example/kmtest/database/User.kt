package com.example.kmtest.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = false)
    val id : Int,
    val firstName : String,
    val lastName : String,
    val avatar : String,
    val email : String,
)
