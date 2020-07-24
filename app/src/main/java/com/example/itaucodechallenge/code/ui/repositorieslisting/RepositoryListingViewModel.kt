package com.example.itaucodechallenge.code.ui.repositorieslisting;

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.itaucodechallenge.code.domain.entities.Repository
import com.example.itaucodechallenge.code.repository.DataSourceFactory
import java.util.concurrent.Executors


class LiveDataRepositoryListViewModel : ViewModel() {

    val repositoryListLiveData: LiveData<PagedList<Repository?>> by lazy {
        val dataSourceFactory = DataSourceFactory(viewModelScope)
        val mLivePagedListBuilder: LivePagedListBuilder<String?, Repository?> = LivePagedListBuilder(dataSourceFactory, 5)
        mLivePagedListBuilder
                .setFetchExecutor(Executors.newFixedThreadPool(5))
                .build()
    }

}
