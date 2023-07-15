package com.example.kmtest.database

import androidx.paging.PagingSource
import androidx.room.*
import com.example.kmtest.model.network.DataItem

@Dao
interface UserDao {
    @Query("Select * From users")
    fun getAllUsers() : PagingSource<Int, DataItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user : List<User>)

    @Delete
    fun deleteUser(user : User)

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}