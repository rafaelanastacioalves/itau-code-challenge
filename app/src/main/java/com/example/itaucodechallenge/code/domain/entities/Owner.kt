package com.example.itaucodechallenge.code.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Owner(

        @PrimaryKey
        @ColumnInfo(name = "owner_id")
        var id : Int,
        @ColumnInfo(name = "login")
        var login : String,
        @ColumnInfo(name = "avatar_url") @SerializedName("avatar_url")
        var avatarUrl : String

)