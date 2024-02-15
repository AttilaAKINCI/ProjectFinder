package com.akinci.projectfinder.domain.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Owner(
    val id: Int,
    val name: String,
    val avatarUrl: String,
) : Parcelable
