package com.akinci.projectfinder.domain.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Project(
    val id: Long,
    val name: String,
    val url: String,
    val owner: Owner,
    val starCount: Int,
    val openIssueCount: Int,
    val description: String?,
    val language: String?,
    val isFavorite: Boolean = false,
) : Parcelable
