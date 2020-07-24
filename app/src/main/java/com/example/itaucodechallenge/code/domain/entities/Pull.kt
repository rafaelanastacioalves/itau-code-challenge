package com.example.itaucodechallenge.code.domain.entities

import com.google.gson.annotations.SerializedName

data class Pull(

        val creator: String,
        val repoName: String,
        val id: Int,
        val order: Int,
        val title: String,
        val user: Owner,
        val body: String,
        @SerializedName("html_url") val pullUrl: String

)
