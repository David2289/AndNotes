package com.example.display.business.datasource.local.androom.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.display.business.datasource.local.androom.dao.UserDao
import com.example.display.business.model.User

@Database(
    entities = arrayOf(User::class),
    version = 1,
    exportSchema = false
)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}