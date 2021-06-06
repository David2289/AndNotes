package com.example.photos.di.module

import android.app.Application
import androidx.room.Room
import com.example.commons.utility.helper.Constants
import com.example.photos.business.datasource.local.PhotosLocalDataSource
import com.example.photos.business.datasource.local.androom.dao.PictureDao
import com.example.photos.business.datasource.local.androom.database.PictureDatabase
import com.example.photos.business.datasource.remote.PhotosRemoteDataSource
import dagger.Module
import dagger.Provides

@Module
class DataSourceModule {

    @Provides
    fun providePictureDatabase(context: Application): PictureDatabase {
        return Room.databaseBuilder(context, PictureDatabase::class.java, Constants.PICTURE_DATABASE)
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun providePictureDao(pictureDatabase: PictureDatabase): PictureDao {
        return pictureDatabase.pictureDao()
    }

    @Provides
    fun provideLocalDataSource(pictureDao: PictureDao): PhotosLocalDataSource {
        return PhotosLocalDataSource(pictureDao)
    }

    @Provides
    fun provideRemoteDataSource(): PhotosRemoteDataSource {
        return PhotosRemoteDataSource()
    }

}