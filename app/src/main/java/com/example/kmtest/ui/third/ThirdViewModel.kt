package com.example.kmtest.ui.third

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kmtest.database.User
import com.example.kmtest.model.network.DataItem
import com.example.kmtest.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ThirdViewModel(private val userRepository : UserRepository): ViewModel() {
    private var userData =
        MutableLiveData<PagingData<DataItem>>().apply { value = PagingData.empty() }

    fun getUsers() : LiveData<PagingData<DataItem>> {
        viewModelScope.launch {
            userRepository.getUsers()
                .cachedIn(viewModelScope)
                .asFlow().collect() {pagingData ->
                    userData.postValue(pagingData)
                }
        }
        return userData
    }


    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            userRepository.deleteStory()
        }
    }
}