package com.akinci.repolisting.features.repositoryViewer.repoListing.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.local.DBConstants.Companion.DB_NAME

@Database( entities = [RepoFavoriteInfoEntity::class], version = 1, exportSchema = false)
abstract class RepoDatabase : RoomDatabase() {

    abstract val repoDAO: RepoDAO

    companion object {
        @Volatile
        private var INSTANCE: RepoDatabase? = null

        fun getInstance(context: Context): RepoDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RepoDatabase::class.java,
                        DB_NAME
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}