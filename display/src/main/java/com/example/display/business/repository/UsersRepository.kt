package com.example.display.business.repository

import com.example.display.business.datasource.local.UsersLocalDataSource
import com.example.display.business.datasource.remote.UsersRemoteDataSource
import com.example.display.business.model.User
import com.example.display.business.model.Users
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UsersRepository @Inject constructor(private val remoteDataSource: UsersRemoteDataSource,
                                          private val localDataSource: UsersLocalDataSource)  {

    fun getUsers(page: Int): Single<List<User>> {
        val userList = localDataSource.getUserList(page)
        return if (userList.isNotEmpty()) {
            Single.just(userList)
        }
        else {
            remoteDataSource.fetchUsers(page)
                    .doOnSuccess(::saveUsers)
                    .flatMap { response -> Observable.fromIterable(response.data).toList() }
        }
    }

    private fun saveUsers(users: Users) {
        for (i in users.data.indices) {
            users.data[i].page = users.page
            localDataSource.saveUser(users.data[i])
        }
    }

}