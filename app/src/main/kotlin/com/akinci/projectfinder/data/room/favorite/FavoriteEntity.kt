package com.akinci.projectfinder.data.room.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.akinci.projectfinder.domain.data.Favorite
import com.akinci.projectfinder.data.room.AppDatabaseKeys

@Entity(tableName = AppDatabaseKeys.DB_TABLE_FAVORITES)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val repositoryId: Long,
)
