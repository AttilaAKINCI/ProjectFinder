package com.akinci.projectfinder.data.favorite.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: FavoriteEntity)

    @Query("DELETE FROM DB_TABLE_FAVORITES WHERE repositoryId= :repositoryId")
    suspend fun delete(repositoryId: Long)

    @Query("SELECT count(*) from DB_TABLE_FAVORITES WHERE repositoryId= :repositoryId")
    suspend fun favoriteCount(repositoryId: Long): Int

    @Query("SELECT * from DB_TABLE_FAVORITES ORDER BY id ASC")
    fun getFavorites(): Flow<List<FavoriteEntity>>
}