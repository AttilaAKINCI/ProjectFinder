package com.akinci.projectfinder.ui.features.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.akinci.projectfinder.core.compose.reduce
import com.akinci.projectfinder.ui.features.detail.ProjectDetailViewContract.ScreenArgs
import com.akinci.projectfinder.ui.features.detail.ProjectDetailViewContract.State
import com.akinci.projectfinder.ui.features.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProjectDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val screenArgs by lazy { savedStateHandle.navArgs<ScreenArgs>() }

    private val _stateFlow: MutableStateFlow<State> = MutableStateFlow(
        State(repository = screenArgs.project)
    )
    val stateFlow = _stateFlow.asStateFlow()

    fun toggleFavorite(isFavorite: Boolean) {
        _stateFlow.reduce {
            copy(
                repository = _stateFlow.value.repository.copy(
                    isFavorite = isFavorite.not()
                )
            )
        }
    }
}
