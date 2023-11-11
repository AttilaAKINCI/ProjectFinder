package com.akinci.projectfinder.data.favorite

import com.akinci.projectfinder.core.storage.AppDatabase
import com.akinci.projectfinder.data.favorite.local.Favorite
import com.akinci.projectfinder.data.favorite.local.FavoriteEntity
import com.akinci.projectfinder.data.favorite.local.getData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val appDatabase: AppDatabase,
) {

    suspend fun saveToFavorites(repositoryId: Long) =
        runCatching {
            appDatabase.favoriteDao().insert(
                FavoriteEntity(repositoryId = repositoryId)
            )
        }

    suspend fun removeFromFavorites(repositoryId: Long) =
        runCatching {
            appDatabase.favoriteDao().delete(repositoryId = repositoryId)
        }

    suspend fun isFavorite(repositoryId: Long) = runCatching {
        appDatabase.favoriteDao().favoriteCount(repositoryId) > 0
    }.getOrDefault(false)

    fun getFavorites(): Flow<List<Favorite>> =
        appDatabase.favoriteDao().getFavorites()
            .map {
                it.map { favoriteEntity -> favoriteEntity.getData() }
            }
}
