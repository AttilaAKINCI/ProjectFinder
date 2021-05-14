package com.akinci.projectfinder.features.dashboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akinci.projectfinder.common.coroutine.CoroutineContextProvider
import com.akinci.projectfinder.common.helper.Resource
import com.akinci.projectfinder.features.repocommon.data.output.RepoResponse
import com.akinci.projectfinder.features.repocommon.repository.RepoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RepoDashboardViewModel @Inject constructor(
    private val coroutineContext : CoroutineContextProvider,
    private val repoRepository : RepoRepository,
) : ViewModel() {

    // repo list data
    private val _listData = MutableLiveData<Resource<List<RepoResponse>>>()
    val listData : LiveData<Resource<List<RepoResponse>>> = _listData

    init {
        Timber.d("RepoDashboardViewModel created..")
    }

    fun fetchRepositories(userName : String){
        // fetch data once
        if(_listData.value == null){
            viewModelScope.launch(coroutineContext.IO) {
                Timber.tag("fetchRepos-VMScope").d("Top-level: current thread is ${Thread.currentThread().name}")

                // send shimmer loading effect for first load
                _listData.postValue(Resource.Loading())
                delay(1000) // simulate network delay

                when(val repoResponse = repoRepository.fetchUserRepository(userName)){
                    is Resource.Success -> {
                        repoResponse.data?.let { data ->
                            Timber.d("Repo api service fetched ${data.size} repos")
                            _listData.postValue(Resource.Success(data))
                        }
                    }
                    is Resource.Error -> {
                        // error occurred while fetching repos
                        _listData.postValue(Resource.Error(repoResponse.message))
                    }
                }
            }
        }
    }

//    fun getSelectedRepository(selectedPosition : Int) : RepoResponse {
//
//    }

}