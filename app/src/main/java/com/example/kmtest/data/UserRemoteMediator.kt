package com.example.kmtest.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.kmtest.database.RemoteKeys
import com.example.kmtest.database.User
import com.example.kmtest.database.UserDatabase
import com.example.kmtest.model.network.DataItem
import com.example.kmtest.network.ApiService


@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val userDatabase: UserDatabase,
    private val apiService: ApiService
) : RemoteMediator<Int, DataItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DataItem>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }
        try {
            val responseData = apiService.getUsers(page, state.config.pageSize)
            val data = responseData.body()
            Log.d("Testing",data.toString())
            val endOfPaginationReached = data?.data?.isEmpty() as Boolean
            userDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    userDatabase.remoteKeysDao().deleteRemoteKeys()
                    userDatabase.userDao().deleteAll()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = data.data.map {
                    RemoteKeys(id = it?.id!!, prevKey = prevKey, nextKey = nextKey)
                }
                val list = data.data.map {
                    User(
                        id = it?.id!!,
                        firstName = it.firstName,
                        lastName = it.lastName,
                        avatar = it.avatar,
                        email = it.email

                    )
                }
                userDatabase.remoteKeysDao().insertAll(keys)
                userDatabase.userDao().insertUser(list)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: java.lang.Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, DataItem>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            userDatabase.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, DataItem>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            userDatabase.remoteKeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, DataItem>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                userDatabase.remoteKeysDao().getRemoteKeysId(id)
            }
        }
    }
}