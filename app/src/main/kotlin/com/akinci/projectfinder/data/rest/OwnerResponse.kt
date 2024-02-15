package com.akinci.projectfinder.data.rest

import com.akinci.projectfinder.domain.data.Owner
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerResponse(
    val id: Int,
    @SerialName("login")
    val name: String,
    @SerialName("avatar_url")
    val avatarUrl: String,
)