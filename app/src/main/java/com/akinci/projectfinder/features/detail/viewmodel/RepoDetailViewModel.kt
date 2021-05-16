package com.akinci.projectfinder.features.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akinci.projectfinder.common.coroutine.CoroutineContextProvider
import com.akinci.projectfinder.features.repocommon.data.local.entities.RepoEntity
import com.akinci.projectfinder.features.repocommon.data.output.RepoResponse
import com.akinci.projectfinder.features.repocommon.data.output.convertRepoResponseToRepoEntity
import com.akinci.projectfinder.features.repocommon.repository.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RepoDetailViewModel @Inject constructor(
    private val coroutineContext : CoroutineContextProvider,
    private val repoRepository : RepoRepository,
) : ViewModel() {

    // repository detail data object
    lateinit var repoData : RepoResponse

    init {
        Timber.d("RepoDetailViewModel created..")
    }

    fun addToFavorites(){
        viewModelScope.launch(coroutineContext.IO) {
            Timber.tag("addToFav-VMScope").d("Top-level: current thread is ${Thread.currentThread().name}")
            repoRepository.insertRepository(repoData)
        }
    }

    fun removeFromFavorites(){
        viewModelScope.launch(coroutineContext.IO) {
            Timber.tag("rmvFrmFav-VMScope").d("Top-level: current thread is ${Thread.currentThread().name}")
            repoRepository.deleteRepository(repoData)
        }
    }

}