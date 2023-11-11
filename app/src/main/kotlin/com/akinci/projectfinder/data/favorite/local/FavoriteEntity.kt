package com.akinci.projectfinder.data.favorite.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.akinci.projectfinder.core.storage.AppDatabaseKeys

@Entity(tableName = AppDatabaseKeys.DB_TABLE_FAVORITES)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val repositoryId: Long,
)

fun FavoriteEntity.getData() = Favorite(repositoryId = repositoryId)