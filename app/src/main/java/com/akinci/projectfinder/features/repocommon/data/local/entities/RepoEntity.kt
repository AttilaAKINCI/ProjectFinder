package com.akinci.projectfinder.features.repocommon.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.akinci.projectfinder.common.room.RoomConfig.Companion.REPO_TABLE_NAME

@Entity(tableName = REPO_TABLE_NAME, indices = [Index(value = ["favoriteRepoId"], unique = true)])
data class RepoEntity constructor(
    @PrimaryKey(autoGenerate = false)
    val favoriteRepoId : Long
)