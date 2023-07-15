package com.example.kmtest.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.example.kmtest.data.UserRemoteMediator
import com.example.kmtest.database.UserDatabase
import com.example.kmtest.model.network.DataItem
import com.example.kmtest.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val apiService: ApiService, private val userDatabase: UserDatabase) {

    @OptIn(ExperimentalPagingApi::class)
    fun getUsers(): LiveData<PagingData<DataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = UserRemoteMediator(userDatabase, apiService),
            pagingSourceFactory = {
                userDatabase.userDao().getAllUsers()
            },
        ).liveData
    }
    suspend fun deleteStory() {
        withContext(Dispatchers.IO) {
            userDatabase.userDao().deleteAll()
        }
    }
}