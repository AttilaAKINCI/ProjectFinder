package com.akinci.projectfinder.ui.features.projects

import com.akinci.projectfinder.core.compose.UIState
import com.akinci.projectfinder.domain.Project
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

object ProjectListViewContract {

    data class State(
        val isServiceError: Boolean = false,
        val isNoData: Boolean = false,
        val isShimmerLoading: Boolean = false,
        val shimmerItemCount: Int = 15,

        val searchText: String,
        val isSearchTextInvalid: Boolean = false,
        val repositories: PersistentList<Project> = persistentListOf(),
    ): UIState
}