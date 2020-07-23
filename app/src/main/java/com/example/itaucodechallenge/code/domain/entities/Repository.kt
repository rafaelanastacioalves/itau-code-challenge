package com.example.itaucodechallenge.code.domain.entities

import androidx.room.*

@Entity
 data class Repository(
        @PrimaryKey var id: Int,
        @ColumnInfo(name = "page") var page: String,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "description") val description: String,
        @ColumnInfo(name = "forks") val forks: Int,
        @ColumnInfo(name = "stargazers_count") val stargazersCount: Int,
        @Embedded var owner: Owner

        )

