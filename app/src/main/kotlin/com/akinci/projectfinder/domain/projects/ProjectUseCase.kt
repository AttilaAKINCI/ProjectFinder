package com.akinci.projectfinder.domain.projects

import com.akinci.projectfinder.data.favorite.FavoritesRepository
import com.akinci.projectfinder.data.projects.ProjectRepository
import javax.inject.Inject

class ProjectUseCase @Inject constructor(
    private val projectRepository: ProjectRepository,
    private val favoritesRepository: FavoritesRepository,
) {

    suspend fun getProjectRepositories(repositoryOwnerName: String) =
        projectRepository.getProjectRepositories(repositoryOwnerName)
            .map { projects ->
                projects.map {
                    it.copy(
                        isFavorite = favoritesRepository.isFavorite(it.id)
                    )
                }
            }
}