package com.example.display.business.datasource.remote

import com.example.display.business.datasource.APIService

class UsersRemoteDataSource constructor(private val apiService: APIService) {

    suspend fun fetchUsers(page: Int) = apiService.fetchUsers(page)

}