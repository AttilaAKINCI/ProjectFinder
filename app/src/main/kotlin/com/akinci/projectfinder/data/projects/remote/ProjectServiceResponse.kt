package com.akinci.projectfinder.data.projects.remote

import com.akinci.projectfinder.domain.Owner
import com.akinci.projectfinder.domain.Project
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class ProjectResponse(
    val id: Long,
    val name: String,
    val url: String,
    val owner: OwnerResponse,
    @JsonNames("stargazers_count")
    val starCount: Int,
    @JsonNames("open_issues_count")
    val openIssueCount: Int,
    val description: String?,
    val language: String?,
) {
    fun toDomain() = Project(
        id = id,
        name = name,
        url = url,
        owner = owner.toDomain(),
        starCount = starCount,
        openIssueCount = openIssueCount,
        description = description,
        language = language,
    )
}

@Serializable
@OptIn(ExperimentalSerializationApi::class)
data class OwnerResponse(
    val id: Int,
    @JsonNames("login")
    val name: String,
    @JsonNames("avatar_url")
    val avatarUrl: String,
) {
    fun toDomain() = Owner(
        id = id,
        name = name,
        avatarUrl = avatarUrl,
    )
}
