package com.akinci.projectfinder.data.mapper

import com.akinci.projectfinder.data.rest.OwnerResponse
import com.akinci.projectfinder.domain.data.Owner

fun OwnerResponse.toDomain() = Owner(
    id = id,
    name = name,
    avatarUrl = avatarUrl,
)