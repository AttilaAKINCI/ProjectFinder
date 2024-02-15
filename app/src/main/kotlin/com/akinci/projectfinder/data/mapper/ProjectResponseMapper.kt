package com.akinci.projectfinder.data.mapper

import com.akinci.projectfinder.data.rest.ProjectResponse
import com.akinci.projectfinder.domain.data.Project

fun ProjectResponse.toDomain() = Project(
    id = id,
    name = name,
    url = url,
    owner = owner.toDomain(),
    starCount = starCount,
    openIssueCount = openIssueCount,
    description = description,
    language = language,
)