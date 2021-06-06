package com.example.photos.di.module

import com.example.photos.business.datasource.local.PhotosLocalDataSource
import com.example.photos.business.datasource.remote.PhotosRemoteDataSource
import com.example.photos.business.repository.PhotosRepository
import dagger.Module
import dagger.Provides

@Module(includes = [DataSourceModule::class])
class RepositoryModule {

    @Provides
    fun provideRepositoryModule(photosRemoteDataSource: PhotosRemoteDataSource,
                                photosLocalDataSource: PhotosLocalDataSource): PhotosRepository {
        return PhotosRepository(photosRemoteDataSource, photosLocalDataSource)
    }

}