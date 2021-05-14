package com.akinci.projectfinder.features.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akinci.projectfinder.common.coroutine.CoroutineContextProvider
import com.akinci.projectfinder.common.helper.Resource
import com.akinci.projectfinder.features.repocommon.data.output.RepoResponse
import com.akinci.projectfinder.features.repocommon.repository.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RepoDetailViewModel @Inject constructor(
    private val coroutineContext : CoroutineContextProvider,
    private val repoRepository : RepoRepository,
) : ViewModel() {

    private val _repoData = MutableLiveData<RepoResponse>()
    val repoData : LiveData<RepoResponse> = _repoData

    init {
        Timber.d("RepoDetailViewModel created..")
    }

//    fun setToFavorites(){
//        repoRepository.insertRepository()
//    }
//
//    fun removeFromFavorites(){
//        repoRepository.deleteRepository()
//    }

    fun insertRepoResponse(repoData : RepoResponse){
        // insert repoData to LiveData
        _repoData.postValue(repoData)
    }
}