package com.example.display.business.repository

import com.example.display.business.datasource.local.UsersLocalDataSource
import com.example.display.business.datasource.remote.UsersRemoteDataSource
import com.example.display.business.model.Users
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UsersRepository @Inject constructor(val remoteDataSource: UsersRemoteDataSource,
                                          val localDataSource: UsersLocalDataSource)  {

    fun getUsers(): Single<Users> {
        return remoteDataSource.fetchUsers()
    }

}