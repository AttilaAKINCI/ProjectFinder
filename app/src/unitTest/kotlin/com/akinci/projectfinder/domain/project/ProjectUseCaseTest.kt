package com.akinci.projectfinder.domain.project

import com.akinci.projectfinder.data.favorite.FavoritesRepository
import com.akinci.projectfinder.data.projects.ProjectRepository
import com.akinci.projectfinder.domain.Owner
import com.akinci.projectfinder.domain.Project
import com.akinci.projectfinder.domain.ProjectUseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ProjectUseCaseTest {

    private val projectRepository: ProjectRepository = mockk(relaxed = true)
    private val favoritesRepository: FavoritesRepository = mockk(relaxed = true)

    private lateinit var testedClass: ProjectUseCase

    @BeforeEach
    fun setup() {
        testedClass = ProjectUseCase(projectRepository, favoritesRepository)
    }

    @Test
    fun `should contain favorite project when project id is locally saved`() = runTest {
        coEvery { favoritesRepository.isFavorite(repositoryId = 1000L) } returns true
        coEvery { projectRepository.getProjectRepositories(any()) } returns Result.success(
            getProjects()
        )

        val result = testedClass.getProjectRepositories("AttilaAKINCI")

        result.isSuccess shouldBe true
        result.getOrNull()!![0].isFavorite shouldBe true
    }

    private fun getProjects() =
        listOf(
            Project(
                id = 1000L,
                name = "Chatter",
                url = "https://www.google.com",
                owner = Owner(id = 100, name = "Attila", avatarUrl = "https://www.google.com"),
                starCount = 10,
                openIssueCount = 0,
                description = "Sample description Chatter",
                language = "Kotlin",
            ),
            Project(
                id = 2000L,
                name = "DoggoApp",
                url = "https://www.google.com",
                owner = Owner(id = 100, name = "Attila", avatarUrl = "https://www.google.com"),
                starCount = 2,
                openIssueCount = 0,
                description = "Sample description Doggo",
                language = "Kotlin",
            )
        )
}