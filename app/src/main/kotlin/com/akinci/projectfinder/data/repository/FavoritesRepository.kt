package com.akinci.projectfinder.data.repository

import com.akinci.projectfinder.data.mapper.toDomain
import com.akinci.projectfinder.data.room.favorite.FavoriteDao
import com.akinci.projectfinder.data.room.favorite.FavoriteEntity
import com.akinci.projectfinder.domain.data.Favorite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val favoriteDao: FavoriteDao,
) {

    suspend fun saveToFavorites(repositoryId: Long) = runCatching {
        favoriteDao.insert(FavoriteEntity(repositoryId = repositoryId))
    }

    suspend fun removeFromFavorites(repositoryId: Long) = runCatching {
        favoriteDao.delete(repositoryId = repositoryId)
    }

    suspend fun isFavorite(repositoryId: Long) = runCatching {
        favoriteDao.favoriteCount(repositoryId) > 0
    }.getOrDefault(false)

    fun getFavorites(): Flow<List<Favorite>> = favoriteDao.getFavorites().map { favorites ->
        favorites.map { it.toDomain() }
    }
}
