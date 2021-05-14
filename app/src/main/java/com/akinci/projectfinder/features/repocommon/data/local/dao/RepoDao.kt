package com.akinci.projectfinder.features.repocommon.data.local.dao

import androidx.room.*
import com.akinci.projectfinder.features.repocommon.data.local.entities.RepoEntity

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllRepositories(repos : List<RepoEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRepository(repo : RepoEntity)

    @Delete
    suspend fun deleteRepository(repo : RepoEntity)

    @Query("SELECT * FROM repoTable")
    suspend fun getAllRepositories(): List<RepoEntity>

    @Query("SELECT * FROM repoTable WHERE favoriteRepoId= :favoriteRepoId")
    suspend fun getRepository(favoriteRepoId : Long): RepoEntity

}