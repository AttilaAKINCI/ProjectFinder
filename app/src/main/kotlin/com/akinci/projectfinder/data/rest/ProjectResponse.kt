package com.akinci.projectfinder.data.rest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProjectResponse(
    val id: Long,
    val name: String,
    val url: String,
    val owner: OwnerResponse,
    @SerialName("stargazers_count")
    val starCount: Int,
    @SerialName("open_issues_count")
    val openIssueCount: Int,
    val description: String?,
    val language: String?,
)
