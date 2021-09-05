package com.example.display.business.datasource

import com.example.display.business.model.Users
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("api/users")
    suspend fun fetchUsers(@Query("page") page: Int): Response<Users>

    @GET("api/users")
    fun fetchUsersCall(): Call<Users>

}