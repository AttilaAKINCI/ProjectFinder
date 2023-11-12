package com.akinci.projectfinder.ui.features.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akinci.projectfinder.core.compose.reduce
import com.akinci.projectfinder.core.coroutine.ContextProvider
import com.akinci.projectfinder.data.favorite.FavoritesRepository
import com.akinci.projectfinder.ui.features.detail.ProjectDetailViewContract.ScreenArgs
import com.akinci.projectfinder.ui.features.detail.ProjectDetailViewContract.State
import com.akinci.projectfinder.ui.features.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProjectDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val contextProvider: ContextProvider,
    private val favoritesRepository: FavoritesRepository,
) : ViewModel() {

    private val screenArgs by lazy { savedStateHandle.navArgs<ScreenArgs>() }

    private val _stateFlow: MutableStateFlow<State> = MutableStateFlow(
        State(repository = screenArgs.project)
    )
    val stateFlow = _stateFlow.asStateFlow()

    fun toggleFavorite(isFavorite: Boolean) {
        viewModelScope.launch {
            withContext(contextProvider.io) {
                if (isFavorite) {
                    favoritesRepository.removeFromFavorites(
                        repositoryId = _stateFlow.value.repository.id
                    )
                } else {
                    favoritesRepository.saveToFavorites(
                        repositoryId = _stateFlow.value.repository.id
                    )
                }
            }.onSuccess {
                // favorite successfully processed on local storage
                //  we can update ui state.
                _stateFlow.reduce {
                    copy(
                        repository = _stateFlow.value.repository.copy(
                            isFavorite = isFavorite.not()
                        )
                    )
                }
            }
        }
    }
}
