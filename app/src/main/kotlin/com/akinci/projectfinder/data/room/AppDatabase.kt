package com.akinci.projectfinder.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.akinci.projectfinder.data.room.favorite.FavoriteDao
import com.akinci.projectfinder.data.room.favorite.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getFavoriteDao(): FavoriteDao
}