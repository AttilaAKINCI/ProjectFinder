package com.akinci.projectfinder.ui.features.projects

import app.cash.turbine.test
import com.akinci.projectfinder.core.coroutines.MainDispatcherRule
import com.akinci.projectfinder.core.coroutines.TestContextProvider
import com.akinci.projectfinder.core.network.exception.NotFound
import com.akinci.projectfinder.data.favorite.FavoritesRepository
import com.akinci.projectfinder.domain.Owner
import com.akinci.projectfinder.domain.Project
import com.akinci.projectfinder.domain.ProjectUseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import timber.log.Timber

@ExtendWith(MainDispatcherRule::class)
class ProjectListViewModelTest {

    private val contextProvider = TestContextProvider()
    private val favoritesRepository: FavoritesRepository = mockk(relaxed = true)
    private val projectUseCase: ProjectUseCase = mockk(relaxed = true)

    private lateinit var testedClass: ProjectListViewModel

    @BeforeEach
    fun setup() {
        mockkObject(Timber)
        testedClass = ProjectListViewModel(
            contextProvider,
            projectUseCase,
            favoritesRepository,
        )
    }

    @AfterEach
    fun release() {
        unmockkObject(Timber)
    }

    @Test
    fun `should update searchText when updateSearchValue called`() = runTest {
        testedClass.updateSearchValue("AttilaAKINCI")

        testedClass.stateFlow.test {
            val state = awaitItem()
            state.searchText shouldBe "AttilaAKINCI"
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `should set isSearchTextInvalid true when empty searchText detected`() = runTest {
        testedClass.updateSearchValue("")
        testedClass.search()

        testedClass.stateFlow.test {
            val state = awaitItem()
            state.isSearchTextInvalid shouldBe true
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `should set isNoData true when empty repository list is detected`() = runTest {
        coEvery { projectUseCase.getProjectRepositories(any()) } returns Result.success(listOf())

        testedClass.updateSearchValue("AttilaAKINCI")
        testedClass.search()

        testedClass.stateFlow.test {
            awaitItem().isLoading shouldBe true
            awaitItem().isNoData shouldBe true
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `should receive repositories when success case`() = runTest {
        coEvery { projectUseCase.getProjectRepositories(any()) } returns Result.success(
            getProjects()
        )

        testedClass.updateSearchValue("AttilaAKINCI")
        testedClass.search()

        testedClass.stateFlow.test {
            awaitItem().isLoading shouldBe true

            val state = awaitItem()
            state.isNoData shouldBe false
            state.isServiceError shouldBe false
            state.isLoading shouldBe false
            state.repositories.size shouldBe 2
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `should isNoData true when NotFound service exception`() = runTest {
        coEvery { projectUseCase.getProjectRepositories(any()) } returns Result.failure(
            NotFound("Repository Owner couldn't found")
        )

        testedClass.updateSearchValue("AttilaAKINCI")
        testedClass.search()

        testedClass.stateFlow.test {
            awaitItem().isLoading shouldBe true

            val state = awaitItem()
            state.isNoData shouldBe true
            state.isServiceError shouldBe false
            state.isLoading shouldBe false
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `should return isServiceError true when unknown service exception`() = runTest {
        coEvery { projectUseCase.getProjectRepositories(any()) } returns Result.failure(
            Throwable("Unknown Exception")
        )

        testedClass.updateSearchValue("AttilaAKINCI")
        testedClass.search()

        testedClass.stateFlow.test {
            awaitItem().isLoading shouldBe true

            val state = awaitItem()
            state.isServiceError shouldBe true
            state.isLoading shouldBe false
            ensureAllEventsConsumed()
        }

        // check unknown error is logged or not.
        verify(exactly = 1) { Timber.e(any() as Exception, any() as String) }
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