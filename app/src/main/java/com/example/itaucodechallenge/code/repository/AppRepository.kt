package com.example.itaucodechallenge.code.repository

import com.example.itaucodechallenge.code.domain.entities.Pull
import com.example.itaucodechallenge.code.domain.entities.RepositoryAnswer
import com.example.itaucodechallenge.code.domain.entities.Resource
import com.example.itaucodechallenge.code.repository.database.AppDataBase
import com.example.itaucodechallenge.code.repository.database.DAO
import com.example.itaucodechallenge.code.repository.http.APIClient
import com.example.itaucodechallenge.code.repository.http.ServiceGenerator

object AppRepository {
    private val appDao: DAO = AppDataBase.getInstance().appDAO()
    var apiClient: APIClient = ServiceGenerator.createService(APIClient::class.java);

    suspend fun repository(page: String): Resource<RepositoryAnswer> {

        return object : NetworkBoundResource<RepositoryAnswer, RepositoryAnswer>() {
            private val page:String = page

            override suspend fun makeCall(): RepositoryAnswer? {
                return apiClient.getRepositoryList(this.page,"language:Java", "star" )
            }

            override suspend fun getFromDB(): RepositoryAnswer? {
                val repositoryList = appDao.getRepositoryList(page)
                return if(repositoryList.isNotEmpty()){
                    RepositoryAnswer(repositoryList)
                }else{
                    null
                }
            }
            override suspend fun saveIntoDB(resultData: RepositoryAnswer?) {
                resultData?.items?.forEach { item -> item.page = this.page }
                appDao.saveRepositoryList(resultData?.items)
            }

        }.fromHttpAndDB()
    }


}