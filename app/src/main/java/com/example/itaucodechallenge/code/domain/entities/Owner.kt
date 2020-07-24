package com.example.itaucodechallenge.code.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Owner(

        @PrimaryKey
        @ColumnInfo(name = "owner_id")
        val id : Int,
        @ColumnInfo(name = "login")
        val login : String,
        @ColumnInfo(name = "avatar_url") @SerializedName("avatar_url")
        val avatarUrl : String

)