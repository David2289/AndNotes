package com.example.display.business.model

import com.google.gson.annotations.SerializedName

class Users (
    @SerializedName("page") var page: Int,
    @SerializedName("per_page") var perPage: Int,
    @SerializedName("total") var total: Int,
    @SerializedName("total_pages") var total_pages: Int,
    @SerializedName("data") var data: List<User>,
        )