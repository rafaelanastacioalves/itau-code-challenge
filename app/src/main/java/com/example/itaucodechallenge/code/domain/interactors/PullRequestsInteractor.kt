package com.example.itaucodechallenge.code.domain.interactors


import com.example.itaucodechallenge.code.domain.entities.Pull
import com.example.itaucodechallenge.code.domain.entities.Resource
import com.example.itaucodechallenge.code.repository.AppRepository

class PullRequestsInteractor :
        Interactor<Resource<List<Pull>>?, PullRequestsInteractor.RequestValues>() {
    val appRepository: AppRepository

    init {
        appRepository = AppRepository
    }

    override suspend fun run(requestValue: PullRequestsInteractor.RequestValues?): Resource<List<Pull>>? {
        var result = requestValue?.creator?.let {
            appRepository.entityDetails(it, requestValue.repository)
        }
        return result
    }

    class RequestValues(val creator: String, val repository: String) : Interactor.RequestValues

}
