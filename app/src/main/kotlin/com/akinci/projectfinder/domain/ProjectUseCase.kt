package com.akinci.projectfinder.domain

import com.akinci.projectfinder.data.repository.FavoritesRepository
import com.akinci.projectfinder.data.repository.ProjectRepository
import javax.inject.Inject

class ProjectUseCase @Inject constructor(
    private val projectRepository: ProjectRepository,
    private val favoritesRepository: FavoritesRepository,
) {

    suspend fun getProjectRepositories(repositoryOwnerName: String) =
        projectRepository.getProjectRepositories(repositoryOwnerName)
            .map { projects ->
                projects.map {
                    it.copy(isFavorite = favoritesRepository.isFavorite(it.id))
                }
            }
}
