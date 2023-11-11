package com.akinci.projectfinder.ui.features.projects

import com.akinci.projectfinder.domain.projects.Project
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

object ProjectListViewContract {

    data class State(
        val isServiceError: Boolean = false,
        val isNoData: Boolean = false,
        val isLoading: Boolean = false,

        val searchText: String,
        val isSearchTextInvalid: Boolean = false,
        val repositories: PersistentList<Project> = persistentListOf(),
    )
}