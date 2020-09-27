package com.akinci.repolisting.features.repositoryViewer.repoListing.data.api

import com.akinci.repolisting.commons.data.remote.RetrofitClient

open class RepoListServiceHelper {
    companion object {
        @JvmStatic val GITHUB_REST_API_URL = "https://api.github.com/"

        // Repo List Service
        fun getRepoListService(): RepoListService = RetrofitClient.getClient(
            GITHUB_REST_API_URL
        ).create(
            RepoListService::class.java)
    }
}