package com.example.kmtest.database

import androidx.room.*

@Dao
interface UserDao {
    @Query("Select * From users")
    fun getAllUsers() : List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user : User)

    @Delete
    fun deleteUser(user : User)
}