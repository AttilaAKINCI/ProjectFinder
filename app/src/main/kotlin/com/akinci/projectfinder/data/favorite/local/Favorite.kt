package com.akinci.projectfinder.data.favorite.local

data class Favorite(
    val repositoryId: Long,
)

fun Favorite.getEntity() = FavoriteEntity(repositoryId = repositoryId)