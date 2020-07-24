package com.example.itaucodechallenge.code.repository.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.itaucodechallenge.code.domain.entities.Repository

@Dao
interface DAO {

    @Query("SELECT * FROM repository WHERE page = :page ORDER BY stargazers_count DESC")
    suspend fun getRepositoryList(page : String): List<Repository>

    @Insert
    suspend fun saveRepositoryList(resultData: List<Repository>?)

    @Delete
    suspend fun delete(repository: Repository)

    @Query("DELETE FROM repository")
    suspend fun delteAllRepositories()
}