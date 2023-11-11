package com.akinci.projectfinder.ui.features.projects

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akinci.projectfinder.core.compose.reduce
import com.akinci.projectfinder.core.coroutine.ContextProvider
import com.akinci.projectfinder.core.network.exception.NotFound
import com.akinci.projectfinder.data.projects.ProjectRepository
import com.akinci.projectfinder.ui.features.projects.ProjectListViewContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProjectListViewModel @Inject constructor(
    private val contextProvider: ContextProvider,
    private val projectRepository: ProjectRepository,
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<State> = MutableStateFlow(
        State(searchText = "")
    )
    val stateFlow = _stateFlow.asStateFlow()

    fun updateSearchValue(searchText: String) {
        _stateFlow.reduce {
            copy(
                searchText = searchText,
                isSearchTextInvalid = false,
                isServiceError = false,
                isNoData = false,
                isLoading = false,
                repositories = persistentListOf(),
            )
        }
    }

    fun search() {
        val searchText = _stateFlow.value.searchText

        if (searchText.isNotBlank()) {
            // switch UI to loading mode.
            _stateFlow.reduce {
                copy(isLoading = true)
            }

            viewModelScope.launch {
                val state = withContext(contextProvider.io) {
                    projectRepository.getProjectRepositories(
                        repositoryOwnerName = searchText
                    )
                }.fold(
                    onSuccess = {
                        if (it.isNotEmpty()) {
                            _stateFlow.value.copy(
                                isServiceError = false,
                                isNoData = false,
                                isLoading = false,
                                repositories = it.toPersistentList()
                            )
                        } else {
                            _stateFlow.value.copy(
                                isServiceError = false,
                                isNoData = true,
                                isLoading = false,
                            )
                        }
                    },
                    onFailure = {
                        when (it) {
                            is NotFound -> {
                                // Endpoint returns 404 for not existing user requests.
                                // this requests should be mapped to no data case
                                _stateFlow.value.copy(
                                    isServiceError = false,
                                    isNoData = true,
                                    isLoading = false,
                                )
                            }

                            else -> {
                                // log unexpected error
                                Timber.e(it, "Project fetch error occurred.")

                                // Unexpected service error case
                                _stateFlow.value.copy(
                                    isServiceError = true,
                                    isLoading = false,
                                )
                            }
                        }
                    }
                )

                // represent network delay here
                delay(1000)

                _stateFlow.value = state
            }
        } else {
            _stateFlow.reduce {
                copy(isSearchTextInvalid = true)
            }
        }
    }
}