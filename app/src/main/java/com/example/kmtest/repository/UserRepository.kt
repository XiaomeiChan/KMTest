package com.example.kmtest.repository

import com.example.kmtest.database.UserDatabase
import com.example.kmtest.network.ApiService

class UserRepository(private val apiService: ApiService, private val userDatabase: UserDatabase) {

    fun getUsers() = apiService
}