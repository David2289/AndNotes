package com.example.display.di.module

import com.example.display.business.datasource.local.UsersLocalDataSource
import com.example.display.business.datasource.remote.UsersRemoteDataSource
import com.example.display.business.repository.UsersRepository
import dagger.Module
import dagger.Provides

@Module(includes = arrayOf(DataSourceModule::class))
class RepositoryModule {

    @Provides
    fun provideUsersRepository(remoteDataSource: UsersRemoteDataSource,
                               localDataSource: UsersLocalDataSource): UsersRepository {
        return UsersRepository(remoteDataSource, localDataSource)
    }

}