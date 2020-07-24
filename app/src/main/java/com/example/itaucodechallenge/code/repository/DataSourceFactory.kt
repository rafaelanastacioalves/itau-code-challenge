package com.example.itaucodechallenge.code.repository;

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.itaucodechallenge.code.domain.entities.Repository
import kotlinx.coroutines.CoroutineScope


class DataSourceFactory(private val viewModelScope: CoroutineScope) : DataSource.Factory<String?, Repository?>() {
    private val sourceLiveData: MutableLiveData<PagedRepoDataSource> = MutableLiveData()
    override fun create(): DataSource<String?, Repository?>? {
        val source = PagedRepoDataSource(viewModelScope)
        sourceLiveData.postValue(source)
        return source
    }


}