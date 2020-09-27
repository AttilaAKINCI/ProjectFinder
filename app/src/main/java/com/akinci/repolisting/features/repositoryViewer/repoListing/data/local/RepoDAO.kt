package com.akinci.repolisting.features.repositoryViewer.repoListing.data.local

import androidx.room.*
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.local.DBConstants.Companion.REPOS_TABLE_NAME
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.local.DBConstants.Companion.REPO_ID
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.local.DBConstants.Companion.REPO_OWNER_NAME

@Dao
interface RepoDAO {
    /**
     * @Query, @Insert, @Update, @Delete
     *
     * @Delete annotation works using primary key of entity
     *
     * **/

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(repo : RepoFavoriteInfoEntity)

    @Query("DELETE FROM $REPOS_TABLE_NAME WHERE $REPO_ID= :repoId AND $REPO_OWNER_NAME = :ownerName")
    suspend fun delete(repoId:Long, ownerName : String)

    @Query("SELECT * FROM $REPOS_TABLE_NAME WHERE $REPO_OWNER_NAME = :ownerName")
    suspend fun getAllRepos(ownerName : String) : MutableList<RepoFavoriteInfoEntity>
}