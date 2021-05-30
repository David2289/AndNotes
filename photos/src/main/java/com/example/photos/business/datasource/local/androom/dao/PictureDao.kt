package com.example.photos.business.datasource.local.androom.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.photos.business.datasource.local.androom.entity.PictureEntity

@Dao
interface PictureDao {

    @Query("SELECT * FROM table_picture")
    fun getPictures(): List<PictureEntity>

    @Insert
    fun insertPicture(picture: PictureEntity)

}