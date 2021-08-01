package com.example.display.business.datasource.remote

import com.example.display.business.datasource.APIService
import com.example.display.business.model.Users
import io.reactivex.rxjava3.core.Single

class UsersRemoteDataSource constructor(private val apiService: APIService) {

    fun fetchUsers(page: Int): Single<Users> {
        return apiService.fetchUsers(page)
    }

}