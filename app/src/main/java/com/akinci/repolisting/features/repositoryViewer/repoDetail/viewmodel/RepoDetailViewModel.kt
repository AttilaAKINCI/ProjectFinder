package com.akinci.repolisting.features.repositoryViewer.repoDetail.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.local.RepoDAO
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.model.RepoRowModel
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.model.asDataBaseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class RepoDetailViewModel(val app: Application, private val dataSource:RepoDAO, var detailData: RepoRowModel) : AndroidViewModel(app) {

    private val job = SupervisorJob() // job for coroutine termination
    private val viewModelScope = CoroutineScope(job + Dispatchers.Main) // coroutine scope

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    init { }

    fun getRepoId() : Long {return detailData.id}
    fun getIsFav() : Boolean {return detailData.isFav}

    fun toggleFav(){
        detailData.isFav = !detailData.isFav
        viewModelScope.launch {
            var toastMessage = ""
            if(detailData.isFav){
                dataSource.insert(detailData.asDataBaseModel())
                toastMessage = "Repository is added to favorites"
            }else{
                dataSource.delete(detailData.id, detailData.owner.login)
                toastMessage = "Repository is removed from favorites"
            }

            Toast.makeText(app, toastMessage, Toast.LENGTH_LONG).show()
        }
    }

    /** Factory for constructing RepoListViewModel with parameter **/
    class Factory(val context: Application, private val dataSource:RepoDAO, private val detailData: RepoRowModel) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RepoDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RepoDetailViewModel(context, dataSource, detailData) as T
            }
            throw IllegalArgumentException("repoListViewModel construction failed")
        }
    }
}