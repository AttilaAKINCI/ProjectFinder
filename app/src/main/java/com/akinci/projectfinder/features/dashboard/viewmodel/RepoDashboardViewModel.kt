package com.akinci.projectfinder.features.dashboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akinci.projectfinder.common.coroutine.CoroutineContextProvider
import com.akinci.projectfinder.common.helper.Resource
import com.akinci.projectfinder.features.repocommon.data.local.entities.RepoEntity
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

    // for initial search focus
    var searchFocusEnabled = MutableLiveData(true)

    // repo list data
    private var _lastSearchedUserName = ""
    private var _repoOwnerName = ""
    private var _data = mutableListOf<RepoResponse>()
    private val _listData = MutableLiveData<Resource<List<RepoResponse>>>()
    val listData : LiveData<Resource<List<RepoResponse>>> = _listData

    init {
        Timber.d("RepoDashboardViewModel created..")
    }

    fun updateRepositoryStates() {
        if(_data.isNotEmpty() && _repoOwnerName.isNotBlank()){
            // list content need to be previously fetched.

            viewModelScope.launch(coroutineContext.IO) {
                Timber.tag("fetchFav-VMScope").d("Top-level: current thread is ${Thread.currentThread().name}")

                val repoFavListForSearchedDeveloper = repoRepository.getAllRepositories(_repoOwnerName)

                _data.map {
                    if(repoFavListForSearchedDeveloper.isNotEmpty()){
                        // if I have favorite repository data, consider value of isFavorite using it.
                        it.isFavorite = repoFavListForSearchedDeveloper.filter{ localIds -> localIds.id == it.id}.any()
                    }else{
                        // if we don't have favorite repository data, assume there is not any favorite repository
                        it.isFavorite = false
                    }
                }

                _listData.postValue(Resource.Success(_data))
            }
        }
    }

    // only calls with search button.
    fun fetchRepositories(userName : String) = viewModelScope.launch(coroutineContext.IO) {
        Timber.tag("fetchRepos-VMScope").d("Top-level: current thread is ${Thread.currentThread().name}")

        if(_lastSearchedUserName != userName){
            // Check last searched username so as to prevent unnecessary service call

            _lastSearchedUserName = userName

            // send shimmer loading effect for first load
            _listData.postValue(Resource.Loading())
            delay(1000) // simulate network delay

            when(val repoResponse = repoRepository.fetchUserRepository(userName)){
                is Resource.Success -> {
                    repoResponse.data?.let { data ->
                        Timber.d("Repo api service fetched ${data.size} repos")
                        _repoOwnerName = data.first().owner.login
                        _data = data.toMutableList()
                        updateRepositoryStates()
                    }
                }
                is Resource.Error -> {
                    // error occurred while fetching repos
                    _listData.postValue(Resource.Error(repoResponse.message))
                }
            }
        }else{
            _listData.postValue(Resource.Info("Search Keyword recently used. Please type different search key"))
        }
    }

    fun getSelectedRepository(selectedPosition : Int) : RepoResponse? {
        if(_data.isNotEmpty()){ return _data[selectedPosition] }
        return null
    }

}