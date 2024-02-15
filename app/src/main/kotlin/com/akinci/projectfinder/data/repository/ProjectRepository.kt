package com.akinci.projectfinder.data.repository

import com.akinci.projectfinder.core.network.toResponse
import com.akinci.projectfinder.data.mapper.toDomain
import com.akinci.projectfinder.data.rest.ProjectResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import javax.inject.Inject

class ProjectRepository @Inject constructor(
    private val httpClient: HttpClient,
) {
    suspend fun getProjectRepositories(repositoryOwnerName: String) = runCatching {
        httpClient
            .get("/users/$repositoryOwnerName/repos")
            .toResponse<List<ProjectResponse>>()
            .map { repositories ->
                // favorite information is bind in ProjectUseCase
                repositories.map { it.toDomain() }
            }.getOrThrow()
    }
}
