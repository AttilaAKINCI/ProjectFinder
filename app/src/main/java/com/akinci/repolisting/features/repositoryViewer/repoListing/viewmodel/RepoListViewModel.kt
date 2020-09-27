package com.akinci.repolisting.features.repositoryViewer.repoListing.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.api.RepoListServiceHelper
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.model.RepoRowModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import com.akinci.repolisting.commons.data.coroutines.Result
import com.akinci.repolisting.commons.data.model.ListRowDataContract
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.local.RepoDAO
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.local.RepoDatabase
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.local.asCompareModel

class RepoListViewModel(application: Application) : AndroidViewModel(application) {

    var listContent : MutableList<ListRowDataContract>
    var localDataSource : RepoDAO
    var ownerName = ""

    init {
        listContent = mutableListOf()
        localDataSource = RepoDatabase.getInstance(application).repoDAO
    }

    fun updateFavStatus(repoId:Long, isFav:Boolean){
        if (repoId != -1L){
            listContent.apply {
                if(size > 0){
                    map { rowDAta ->
                        if(rowDAta is RepoRowModel){ if(rowDAta.id == repoId){ rowDAta.isFav = isFav }  }
                    }
                }
            }
        }
    }

    // ONLY FETCH REMOTE
    fun getRemoteData(repoOwnerName : String) = liveData(Dispatchers.IO) {
        emit(Result.loading(data = null))
        try {
            val resp = RepoListServiceHelper.getRepoListService().getRepos(repoOwnerName)
            if(resp.code() != 200){
                emit(Result.error(data = null, message = "Response Code not valid: " + resp.code()))
            }else{
                listContent = mutableListOf()
                resp.body()?.let {

                    val localSavedRepos = localDataSource.getAllRepos(repoOwnerName).asCompareModel()
                    it.map { row -> if(localSavedRepos.containsKey(row.id)){ row.isFav = true } }

                    listContent.addAll(it)
                }

                emit(Result.success(data = listContent))
            }
        } catch (exception: Exception) {
            emit(Result.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    /** Factory for constructing RepoListViewModel with parameter **/
    class Factory(val context: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RepoListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RepoListViewModel(context) as T
            }
            throw IllegalArgumentException("repoListViewModel construction failed")
        }
    }

}