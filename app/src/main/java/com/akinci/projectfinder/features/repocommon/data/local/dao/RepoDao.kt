package com.akinci.projectfinder.features.repocommon.data.local.dao

import androidx.room.*
import com.akinci.projectfinder.features.repocommon.data.local.entities.RepoEntity

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepository(repo : RepoEntity)

    @Delete
    suspend fun deleteRepository(repo : RepoEntity)

    @Query("SELECT * FROM repoTable WHERE ownerName= :ownerName")
    suspend fun getAllRepositories(ownerName: String): List<RepoEntity>

}