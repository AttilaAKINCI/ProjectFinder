package com.akinci.projectfinder.data.favorite

import app.cash.turbine.test
import com.akinci.projectfinder.core.storage.AppDatabase
import com.akinci.projectfinder.data.favorite.local.FavoriteEntity
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class FavoritesRepositoryTest {

    private val appDatabase: AppDatabase = mockk(relaxed = true)

    private lateinit var testedClass: FavoritesRepository

    @BeforeEach
    fun setup() {
        testedClass = FavoritesRepository(appDatabase)
    }

    @Test
    fun `should return success when successful favorite repository insert`() = runTest {
        coEvery { appDatabase.favoriteDao().insert(any()) } returns Unit
        val result = testedClass.saveToFavorites(1000L)

        result.isSuccess shouldBe true
        result.getOrNull()!! shouldBe Unit
    }

    @Test
    fun `should return failure when exception occurred during favorite repository insert`() =
        runTest {
            val exception = Throwable("Insert exception")
            coEvery { appDatabase.favoriteDao().insert(any()) } throws exception

            val result = testedClass.saveToFavorites(1000L)

            result.isFailure shouldBe true
            result.exceptionOrNull()!! shouldBe exception
        }

    @Test
    fun `should return success when successful removal of favorite repository`() = runTest {
        coEvery { appDatabase.favoriteDao().delete(any()) } returns Unit
        val result = testedClass.removeFromFavorites(1000L)

        result.isSuccess shouldBe true
        result.getOrNull()!! shouldBe Unit
    }

    @Test
    fun `should return failure when exception occurred during favorite repository removal`() =
        runTest {
            val exception = Throwable("Delete exception")
            coEvery { appDatabase.favoriteDao().delete(any()) } throws exception
            val result = testedClass.removeFromFavorites(1000L)

            result.isFailure shouldBe true
            result.exceptionOrNull()!! shouldBe exception
        }

    @Test
    fun `should return true when favorite count is grater than 0`() = runTest {
        coEvery { appDatabase.favoriteDao().favoriteCount(any()) } returns 10

        val result = testedClass.isFavorite(1000L)

        result shouldBe true
    }

    @Test
    fun `should return false when favorite count is equal to 0`() = runTest {
        coEvery { appDatabase.favoriteDao().favoriteCount(any()) } returns 0

        val result = testedClass.isFavorite(1000L)

        result shouldBe false
    }

    @Test
    fun `should return false when exception occurred during favorite count check`() = runTest {
        val exception = Throwable("Query exception")
        coEvery { appDatabase.favoriteDao().favoriteCount(any()) } throws exception

        val result = testedClass.isFavorite(1000L)

        result shouldBe false
    }

    @Test
    fun `should receive flow of favorites when there is locally saved favorites`() = runTest {
        coEvery { appDatabase.favoriteDao().getFavorites() } returns flowOf(
            listOf(
                FavoriteEntity(id = 0, repositoryId = 1000L),
                FavoriteEntity(id = 1, repositoryId = 1001L),
                FavoriteEntity(id = 2, repositoryId = 1002L),
            ),
            listOf(
                FavoriteEntity(id = 3, repositoryId = 1003L),
            )
        )

        testedClass.getFavorites().test {
            awaitItem().size shouldBe 3
            awaitItem().size shouldBe 1
            awaitComplete()
        }
    }

    @Test
    fun `should throw exception when exception occurred during flow query`() = runTest {
        val exception = Throwable("Flow Query exception")
        coEvery { appDatabase.favoriteDao().getFavorites() } throws exception

        assertThrows<Throwable> {
            testedClass.getFavorites()
        }
    }
}
