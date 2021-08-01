package com.example.photos.modules

import android.content.Context
import androidx.room.Room
import com.example.commons.utility.helper.Constants
import com.example.photos.business.datasource.local.PhotosLocalDataSource
import com.example.photos.business.datasource.local.androom.dao.PictureDao
import com.example.photos.business.datasource.local.androom.database.PictureDatabase
import com.example.photos.business.datasource.remote.PhotosRemoteDataSource
import com.example.photos.business.repository.PhotosRepository
import com.example.photos.ui.viewmodel.PhotosViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

var photosModule = module {
    single {
        PhotosLocalDataSource(pictureDao(androidContext()))
    }
    single {
        PhotosRemoteDataSource()
    }
    single {
        PhotosRepository(get(), get())
    }
}

var photoVMModule = module {
    viewModel { PhotosViewModel(get()) }
}

private fun pictureDatabase(context: Context): PictureDatabase {
    return Room.databaseBuilder(context, PictureDatabase::class.java, Constants.PICTURE_DATABASE)
        .allowMainThreadQueries()
        .build()
}

private fun pictureDao(context: Context): PictureDao {
    return pictureDatabase(context).pictureDao()
}