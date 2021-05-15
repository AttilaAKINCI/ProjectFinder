package com.akinci.projectfinder.features.repocommon.repository

import com.akinci.projectfinder.common.helper.Resource
import com.akinci.projectfinder.common.network.NetworkChecker
import com.akinci.projectfinder.common.repository.BaseRepositoryImpl
import com.akinci.projectfinder.features.repocommon.data.api.RepoServiceDao
import com.akinci.projectfinder.features.repocommon.data.local.dao.RepoDao
import com.akinci.projectfinder.features.repocommon.data.local.entities.RepoEntity
import com.akinci.projectfinder.features.repocommon.data.output.RepoResponse
import javax.inject.Inject

class RepoRepository @Inject constructor(
    private val repoServiceDao: RepoServiceDao,
    private val repoDao: RepoDao,
    networkChecker: NetworkChecker
) : BaseRepositoryImpl(networkChecker) {

    suspend fun fetchUserRepository(userName: String): Resource<List<RepoResponse>>
        = callService { repoServiceDao.getUserRepositories(userName) }

    suspend fun insertAllRepositories(repos : List<RepoEntity>) = repoDao.insertAllRepositories(repos)
    suspend fun insertRepository(repo : RepoEntity) = repoDao.insertRepository(repo)
    suspend fun deleteRepository(repo : RepoEntity) = repoDao.deleteRepository(repo)
    fun getAllRepositories() = repoDao.getAllRepositories()

}