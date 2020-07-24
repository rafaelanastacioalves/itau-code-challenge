package com.example.itaucodechallenge.code.repository.http;


import com.example.itaucodechallenge.code.domain.entities.Pull
import com.example.itaucodechallenge.code.domain.entities.RepositoryAnswer
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIClient {

    @GET("/search/repositories")
    suspend fun getRepositoryList(
            @Query("page") page: String,
            @Query("q") language: String,
            @Query("sort") sort: String
    ) : RepositoryAnswer


}
