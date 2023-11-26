package com.akinci.projectfinder.domain

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

@Parcelize
data class Owner(
    val id: Int,
    val name: String,
    val avatarUrl: String,
) : Parcelable
