package com.example.display.business.repository

import com.example.display.business.datasource.local.UsersLocalDataSource
import com.example.display.business.datasource.remote.UsersRemoteDataSource
import com.example.display.business.model.Users

class UsersRepository constructor(private val remoteDataSource: UsersRemoteDataSource,
                                          private val localDataSource: UsersLocalDataSource)  {

    suspend fun getUsers(page: Int) = remoteDataSource.fetchUsers(page)

    private fun saveUsers(users: Users) {
        for (i in users.data.indices) {
            users.data[i].page = users.page
            localDataSource.saveUser(users.data[i])
        }
    }

}