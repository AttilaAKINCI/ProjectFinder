package com.akinci.projectfinder.ui.features.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.akinci.projectfinder.core.coroutines.MainDispatcherRule
import com.akinci.projectfinder.core.coroutines.TestContextProvider
import com.akinci.projectfinder.data.favorite.FavoritesRepository
import com.akinci.projectfinder.domain.Owner
import com.akinci.projectfinder.domain.Project
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainDispatcherRule::class)
class ProjectDetailViewModelTest {

    private val contextProvider = TestContextProvider()
    private val favoritesRepository: FavoritesRepository = mockk(relaxed = true)

    private lateinit var testedClass: ProjectDetailViewModel

    @BeforeEach
    fun setup() {
        testedClass = ProjectDetailViewModel(
            SavedStateHandle(
                mapOf(
                    "project" to Project(
                        id = 1000L,
                        name = "Chatter",
                        url = "https://www.google.com",
                        owner = Owner(
                            id = 100,
                            name = "Attila",
                            avatarUrl = "https://www.google.com"
                        ),
                        starCount = 10,
                        openIssueCount = 0,
                        description = "Sample description Chatter",
                        language = "Kotlin",
                        isFavorite = true
                    )
                )
            ),
            contextProvider,
            favoritesRepository
        )
    }

    @Test
    fun `should toggle favorite true when favorite info removed successfully from local storage`() =
        runTest {
            val initialFavoriteValue = true
            coEvery { favoritesRepository.removeFromFavorites(any()) } returns Result.success(Unit)

            testedClass.toggleFavorite(initialFavoriteValue)

            testedClass.stateFlow.test {
                val state = awaitItem()
                state.repository.isFavorite shouldBe false
                ensureAllEventsConsumed()
            }

            coVerify(exactly = 0) { favoritesRepository.saveToFavorites(any()) }
        }

    @Test
    fun `should toggle favorite false when favorite info saved successfully to local storage`() =
        runTest {
            val initialFavoriteValue = false
            coEvery { favoritesRepository.saveToFavorites(any()) } returns Result.success(Unit)

            testedClass.toggleFavorite(initialFavoriteValue)

            testedClass.stateFlow.test {
                val state = awaitItem()
                state.repository.isFavorite shouldBe true
                ensureAllEventsConsumed()
            }

            coVerify(exactly = 0) { favoritesRepository.removeFromFavorites(any()) }
        }

    @Test
    fun `should protect initial favorite value when favorite info couldn't removed from local storage`() =
        runTest {
            val initialFavoriteValue = true
            coEvery { favoritesRepository.removeFromFavorites(any()) } returns
                    Result.failure(Throwable(""))

            testedClass.toggleFavorite(initialFavoriteValue)

            testedClass.stateFlow.test {
                val state = awaitItem()
                state.repository.isFavorite shouldBe true
                ensureAllEventsConsumed()
            }

            coVerify(exactly = 0) { favoritesRepository.saveToFavorites(any()) }
        }

    @Test
    fun `should protect initial favorite value when favorite info couldn't saved to local storage`() =
        runTest {
            coEvery { favoritesRepository.saveToFavorites(any()) } returns
                    Result.failure(Throwable(""))

            testedClass.toggleFavorite(false)

            testedClass.stateFlow.test {
                val state = awaitItem()
                state.repository.isFavorite shouldBe true
                ensureAllEventsConsumed()
            }

            coVerify(exactly = 0) { favoritesRepository.removeFromFavorites(any()) }
        }
}