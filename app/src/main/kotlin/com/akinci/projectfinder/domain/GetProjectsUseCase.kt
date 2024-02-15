package com.akinci.projectfinder.domain

import com.akinci.projectfinder.data.repository.FavoritesRepository
import com.akinci.projectfinder.data.repository.ProjectRepository
import javax.inject.Inject

class GetProjectsUseCase @Inject constructor(
    private val projectRepository: ProjectRepository,
    private val favoritesRepository: FavoritesRepository,
) {

    suspend fun execute(repositoryOwnerName: String) =
        projectRepository.getProjectRepositories(repositoryOwnerName)
            .map { projects ->
                projects.map {
                    it.copy(isFavorite = favoritesRepository.isFavorite(it.id))
                }
            }
}
