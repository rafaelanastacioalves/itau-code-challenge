package com.example.itaucodechallenge.code.domain.entities

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity
 data class Repository(
        @PrimaryKey val id: Int,
        @ColumnInfo(name = "page") var page: String,
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "description") val description: String,
        @ColumnInfo(name = "forks") val forks: Int,
        @ColumnInfo(name = "stargazers_count")
        @SerializedName("stargazers_count") val stargazersCount: Int,
        @Embedded val owner: Owner

        )


