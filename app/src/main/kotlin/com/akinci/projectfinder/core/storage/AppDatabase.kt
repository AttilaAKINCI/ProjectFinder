package com.akinci.projectfinder.core.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.akinci.projectfinder.data.favorite.local.FavoriteDAO
import com.akinci.projectfinder.data.favorite.local.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDAO

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, AppDatabaseKeys.DB_NAME)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}