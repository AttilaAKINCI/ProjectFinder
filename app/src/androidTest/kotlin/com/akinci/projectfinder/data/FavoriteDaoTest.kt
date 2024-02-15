package com.akinci.projectfinder.data

import com.akinci.projectfinder.di.TestAppModule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.akinci.projectfinder.data.room.AppDatabase
import com.akinci.projectfinder.data.room.favorite.FavoriteDao
import com.akinci.projectfinder.data.room.favorite.FavoriteEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@SmallTest
class FavoriteDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Inject
    @TestAppModule.TestDB
    lateinit var db: AppDatabase

    private lateinit var favoriteDAO: FavoriteDao

    @Before
    fun setup() {
        hiltRule.inject()
        favoriteDAO = db.getFavoriteDao()

        // provide initial data
        runTest {
            favoriteDAO.insert(FavoriteEntity(repositoryId = 1001L))
            favoriteDAO.insert(FavoriteEntity(repositoryId = 1003L))
        }
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun getAllFavoritesFromRoomDB() = runTest {
        val result = favoriteDAO.getFavorites()

        result.test {
            with(awaitItem()){
                size shouldBe 2
                get(0).repositoryId shouldBe 1001L
                get(1).repositoryId shouldBe 1003L
            }

            cancelAndIgnoreRemainingEvents()
        }

        advanceUntilIdle()
    }

    @Test
    fun removeFromRoomDB() = runTest {
        favoriteDAO.delete(1001L)

        val result = favoriteDAO.getFavorites()

        result.test {
            with(awaitItem()){
                size shouldBe 1
                get(0).repositoryId shouldBe 1003L
            }

            cancelAndIgnoreRemainingEvents()
        }

        advanceUntilIdle()
    }

    @Test
    fun checkIsFavoriteRoomDB() = runTest {
        val result = favoriteDAO.favoriteCount(1001L)

        result shouldBeGreaterThan 0

        advanceUntilIdle()
    }
}
