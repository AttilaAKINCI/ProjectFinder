package com.akinci.projectfinder.data.mapper

import com.akinci.projectfinder.data.room.favorite.FavoriteEntity
import com.akinci.projectfinder.domain.data.Favorite

fun FavoriteEntity.toDomain() = Favorite(repositoryId = repositoryId)