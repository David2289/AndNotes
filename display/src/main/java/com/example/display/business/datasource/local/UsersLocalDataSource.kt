package com.example.display.business.datasource.local

import com.example.display.business.datasource.local.androom.dao.UserDao
import com.example.display.business.model.User
import javax.inject.Inject

class UsersLocalDataSource @Inject constructor(private val userDao: UserDao) {

    fun getUserList(page: Int): List<User> {
        return userDao.getUsers(page)
    }

    fun saveUser(user: User) {
        userDao.insertUser(user)
    }

}