package com.example.photos.business.repository

import com.example.photos.business.datasource.local.PhotosLocalDataSource
import com.example.photos.business.datasource.local.androom.entity.PictureEntity
import com.example.photos.business.datasource.remote.PhotosRemoteDataSource
import io.reactivex.rxjava3.core.Single

class PhotosRepository constructor(private val photosRemoteDataSource: PhotosRemoteDataSource,
                                           private val photosLocalDataSource: PhotosLocalDataSource) {

    fun getPictureList(): Single<List<PictureEntity>> {
        return Single.just(photosLocalDataSource.getPictureList())
    }

    fun savePicture(pictureEntity: PictureEntity) {
        photosLocalDataSource.savePicture(pictureEntity)
    }

    fun deletePicture(pictureEntity: PictureEntity) {
        photosLocalDataSource.deletePicture(pictureEntity)
    }

}