package com.akinci.projectfinder.ui.features.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akinci.projectfinder.core.coroutine.ContextProvider
import com.akinci.projectfinder.data.repository.FavoritesRepository
import com.akinci.projectfinder.ui.features.detail.ProjectDetailViewContract.ScreenArgs
import com.akinci.projectfinder.ui.features.detail.ProjectDetailViewContract.State
import com.akinci.projectfinder.ui.features.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
                val repositoryId = stateFlow.value.repository.id
                if (isFavorite) {
                    favoritesRepository.removeFromFavorites(repositoryId = repositoryId)
                } else {
                    favoritesRepository.saveToFavorites(repositoryId = repositoryId)
                }
            }.onSuccess {
                // favorite successfully processed on local storage
                //  we can update ui state.
                _stateFlow.update {
                    it.copy(repository = it.repository.copy(isFavorite = isFavorite.not()))
                }
            }
        }
    }
}
