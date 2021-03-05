package com.example.display.business.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "table_user")
class User(
        @PrimaryKey
        @ColumnInfo(name = "id")
        @SerializedName("id")
        var id: Int,

        @ColumnInfo(name = "page")
        var page: Int,

        @ColumnInfo(name = "email")
        @SerializedName("email")
        var email: String,

        @ColumnInfo(name = "first_name")
        @SerializedName("first_name")
        var firstName: String,

        @ColumnInfo(name = "last_name")
        @SerializedName("last_name")
        var lastName: String,

        @ColumnInfo(name = "avatar")
        @SerializedName("avatar")
        var avatar: String

) : Serializable