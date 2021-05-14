package com.akinci.projectfinder.features.repocommon.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.akinci.projectfinder.features.repocommon.data.local.dao.RepoDao
import com.akinci.projectfinder.features.repocommon.data.local.entities.RepoEntity

@Database(
    entities = [RepoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class  RepoDatabase : RoomDatabase() {
    abstract fun getRepoDao() : RepoDao
}