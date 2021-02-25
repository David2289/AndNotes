package com.example.display.business.datasource

import com.example.display.business.model.Users
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface APIService {

    @GET("api/users")
    fun fetchUsers(): Single<Users>

}