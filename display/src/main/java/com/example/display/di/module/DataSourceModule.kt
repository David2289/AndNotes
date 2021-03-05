package com.example.display.di.module

import android.app.Application
import androidx.room.Room
import com.example.display.BuildConfig
import com.example.display.business.datasource.APIService
import com.example.display.business.datasource.local.UsersLocalDataSource
import com.example.display.business.datasource.local.androom.dao.UserDao
import com.example.display.business.datasource.local.androom.database.UserDatabase
import com.example.display.business.datasource.remote.UsersRemoteDataSource
import com.example.display.ui.utility.helper.Constants
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class DataSourceModule {

    @Provides
    fun provideRetrofit(): Retrofit {

        val httpLoggingInterceptor = HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor) //httpLoggingInterceptor allows to log json body.
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }

    @Provides
    fun provideUserDatabase(context: Application): UserDatabase {
        return Room.databaseBuilder(context, UserDatabase::class.java, Constants.USER_DATABASE)
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideUserDao(userDatabase: UserDatabase): UserDao {
        return userDatabase.userDao()
    }

    @Provides
    fun provideUsersRemoteDataSource(apiService: APIService): UsersRemoteDataSource {
        return UsersRemoteDataSource(apiService)
    }

    @Provides
    fun providesUsersLocalDataSource(userDao: UserDao): UsersLocalDataSource {
        return UsersLocalDataSource(userDao)
    }

}