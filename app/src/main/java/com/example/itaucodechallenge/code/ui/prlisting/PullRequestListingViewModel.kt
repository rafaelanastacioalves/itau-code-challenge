package com.example.itaucodechallenge.code.ui.prlisting


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itaucodechallenge.code.domain.entities.Pull
import com.example.itaucodechallenge.code.domain.entities.Resource
import com.example.itaucodechallenge.code.domain.interactors.PullRequestsInteractor


internal class PullRequestListingViewModel : ViewModel() {

    val entityDetails = MutableLiveData<Resource<List<Pull>>>()

    val pullRequestsInteractor: PullRequestsInteractor = PullRequestsInteractor()

    fun loadData(creator : String, repository : String) : MutableLiveData<Resource<List<Pull>>> {

        entityDetails.postValue(Resource.loading())
        pullRequestsInteractor.execute(viewModelScope, PullRequestsInteractor.RequestValues(creator, repository),{ it -> handle(it)})
        return entityDetails
    }

    private fun handle(it: Resource<List<Pull>>?) {
        entityDetails.postValue(it)
    }


}

