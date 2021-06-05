package com.example.photos.di.module

import com.example.photos.business.repository.PhotosRepository
import dagger.Module
import dagger.Provides

@Module(includes = [DataSourceModule::class])
class RepositoryModule {

    @Provides
    fun provideRepositoryModule(): PhotosRepository {
        return PhotosRepository()
    }

}