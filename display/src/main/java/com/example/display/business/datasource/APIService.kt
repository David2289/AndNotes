package com.example.display.business.datasource

import com.example.display.business.model.Users
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("api/users")
    fun fetchUsers(@Query("page") page: Int): Single<Users>

    @GET("api/users")
    fun fetchUsersObservable(): Observable<Response<Users>>

    @GET("api/users")
    fun fetchUsersCall(): Call<Users>

}