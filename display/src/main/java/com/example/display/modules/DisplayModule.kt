package com.example.display.modules

import android.content.Context
import androidx.room.Room
import com.example.commons.utility.helper.Constants
import com.example.display.BuildConfig
import com.example.display.business.datasource.APIService
import com.example.display.business.datasource.local.UsersLocalDataSource
import com.example.display.business.datasource.local.androom.dao.UserDao
import com.example.display.business.datasource.local.androom.database.UserDatabase
import com.example.display.business.datasource.remote.UsersRemoteDataSource
import com.example.display.business.repository.UsersRepository
import com.example.display.ui.viewmodel.ListViewModel
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val displayModule = module {
    single {
        UsersRemoteDataSource(apiService())
    }
    single {
        UsersLocalDataSource(userDao(androidContext()))
    }
    single {
        UsersRepository(get(), get())
    }
}

val displayVMModule = module {
    viewModel { ListViewModel(get()) }
}

private fun okHttp(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor) //httpLoggingInterceptor allows to log json body.
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .build()
}

private fun retrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(okHttp())
        .build()
}

private fun apiService(): APIService {
    return retrofit().create(APIService::class.java)
}

private fun userDatabase(context: Context): UserDatabase {
    return Room.databaseBuilder(context, UserDatabase::class.java, Constants.USER_DATABASE)
        .allowMainThreadQueries()
        .build()
}

private fun userDao(context: Context): UserDao {
    return userDatabase(context).userDao()
}