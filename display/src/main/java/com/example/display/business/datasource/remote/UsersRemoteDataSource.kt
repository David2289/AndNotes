package com.example.display.business.datasource.remote

import com.example.display.business.datasource.APIService
import com.example.display.business.model.User
import com.example.display.business.model.Users
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UsersRemoteDataSource @Inject constructor(private val apiService: APIService) {

    fun fetchUserList(page: Int): Single<List<User>> {
        return apiService.fetchUsers(page).flatMap { response ->
            Observable.fromIterable(response.data).toList()
        }
    }

    fun fetchUsers(page: Int): Single<Users> {
        return apiService.fetchUsers(page)
    }

}