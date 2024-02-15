package com.akinci.projectfinder.domain.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Favorite(
    val repositoryId: Long,
) : Parcelable