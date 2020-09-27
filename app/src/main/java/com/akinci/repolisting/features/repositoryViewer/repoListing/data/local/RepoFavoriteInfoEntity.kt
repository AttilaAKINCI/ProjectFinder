package com.akinci.repolisting.features.repositoryViewer.repoListing.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.local.DBConstants.Companion.RECORD_ID
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.local.DBConstants.Companion.REPOS_TABLE_NAME
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.local.DBConstants.Companion.REPO_ID
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.local.DBConstants.Companion.REPO_NAME
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.local.DBConstants.Companion.REPO_OWNER_NAME
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.model.OwnerModel
import com.akinci.repolisting.features.repositoryViewer.repoListing.data.model.RepoRowModel

@Entity(tableName = REPOS_TABLE_NAME)
data class RepoFavoriteInfoEntity constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = RECORD_ID)
    var recordId : Long = 0L,

    @ColumnInfo(name = REPO_ID)
    var repoId : Long = 0L,

    @ColumnInfo(name = REPO_NAME)
    var repoName : String = "",

    @ColumnInfo(name = REPO_OWNER_NAME)
    var ownerName : String = "" )
{
    // empty constructor
    constructor() : this(recordId = 0)

    // for a repo
    constructor(repoId: Long, repoName: String, ownerName: String) : this(
        recordId = 0,
        repoId = repoId,
        repoName = repoName,
        ownerName = ownerName
    )
}

/** For integrity between network and ROOM Database **/
fun List<RepoFavoriteInfoEntity>.asCompareModel() : Map<Long, RepoRowModel> {
    var repos = mutableMapOf<Long, RepoRowModel>()

    apply {
        map {
            var repoModel = RepoRowModel(
                it.repoId,
                it.repoName,
                "",
                OwnerModel(it.ownerName),
                0,
                0,
                "",
                "",
                false
            )
            repos.put(it.repoId, repoModel)
        }
    }
    return repos
}