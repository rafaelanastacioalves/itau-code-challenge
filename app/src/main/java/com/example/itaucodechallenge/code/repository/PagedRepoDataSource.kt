package com.example.itaucodechallenge.code.repository

import android.util.Log
import androidx.annotation.NonNull
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.itaucodechallenge.code.domain.entities.Repository
import com.example.itaucodechallenge.code.domain.entities.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PagedRepoDataSource(private val scope: CoroutineScope) : PageKeyedDataSource<String, Repository>() {
    private val loadStatus: MutableLiveData<Boolean?> = MutableLiveData()
    val appRepository: AppRepository

    init {
        appRepository = AppRepository
    }

    fun PagedRepoDataSource() {
        loadStatus.postValue(java.lang.Boolean.TRUE)
    }

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Repository>) {
        loadStatus.postValue(java.lang.Boolean.TRUE)
        scope.launch {
            val repositoryResource = appRepository.repository("1")
            when (repositoryResource.status) {
                Resource.Status.SUCCESS -> {

                    val mutableList = repositoryResource.data?.items as MutableList<Repository>
                    callback.onResult(mutableList,
                            "",
                            (1 + 1).toString())
                }
                Resource.Status.INTERNAL_SERVER_ERROR -> Log.e(javaClass.simpleName, repositoryResource.message)
                Resource.Status.GENERIC_ERROR -> Log.e(javaClass.simpleName, repositoryResource.message)
                Resource.Status.LOADING -> Log.e(javaClass.simpleName, repositoryResource.message)
            }
        }
    }

    fun getLiveLoadStatus(): MutableLiveData<Boolean?>? {
        return loadStatus
    }

    override fun loadAfter(@NonNull params: PageKeyedDataSource.LoadParams<String?>, @NonNull callback: PageKeyedDataSource.LoadCallback<String?, Repository>) {

        loadStatus.postValue(java.lang.Boolean.TRUE)
        scope.launch {
            val repositoryResource = appRepository.repository(params.key.toString())
            when (repositoryResource.status) {
                Resource.Status.SUCCESS -> {
                    callback.onResult(
                            repositoryResource.data?.items as MutableList<Repository>, (Integer.valueOf(params.key) + 1).toString())
                }
                Resource.Status.INTERNAL_SERVER_ERROR -> Log.e(javaClass.simpleName, repositoryResource.message)
                Resource.Status.GENERIC_ERROR -> Log.e(javaClass.simpleName, repositoryResource.message)
                Resource.Status.LOADING -> Log.e(javaClass.simpleName, repositoryResource.message)
            }
        }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Repository>) {
    }


}

