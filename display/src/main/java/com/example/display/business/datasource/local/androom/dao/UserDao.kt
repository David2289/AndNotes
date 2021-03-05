package com.example.display.business.datasource.local.androom.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.display.business.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM table_user")
    fun getUsers(): List<User>

    @Query("SELECT * FROM table_user WHERE page = :page")
    fun getUsers(page: Int): List<User>

    @Insert
    fun insertUser(user: User)

}