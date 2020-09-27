package com.akinci.repolisting.features.repositoryViewer.repoListing.data.api

import com.akinci.repolisting.features.repositoryViewer.repoListing.data.model.RepoRowModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RepoListService {
    @GET("users/{userName}/repos")
    suspend fun getRepos(@Path("userName") userName: String) : Response<MutableList<RepoRowModel>>
}