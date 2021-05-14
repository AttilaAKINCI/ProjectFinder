package com.akinci.projectfinder.features.repocommon.data.api

import com.akinci.projectfinder.common.network.RestConfig
import com.akinci.projectfinder.features.repocommon.data.output.RepoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RepoServiceDao {
    @GET(RestConfig.REPO_OWNER_URL)
    suspend fun getUserRepository(@Path("userName") userName: String) : Response<List<RepoResponse>>
}