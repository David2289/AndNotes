package com.example.photos.business.datasource.local

import com.example.photos.business.datasource.local.androom.dao.PictureDao
import com.example.photos.business.datasource.local.androom.entity.PictureEntity
import javax.inject.Inject

class PhotosLocalDataSource @Inject constructor(private val pictureDao: PictureDao) {

    fun getPictureList(): List<PictureEntity> {
        return pictureDao.getPictures()
    }

    fun savePicture(pictureEntity: PictureEntity) {
        pictureDao.insert(pictureEntity)
    }

    fun deletePicture(pictureEntity: PictureEntity) {
        pictureDao.delete(pictureEntity)
    }

}