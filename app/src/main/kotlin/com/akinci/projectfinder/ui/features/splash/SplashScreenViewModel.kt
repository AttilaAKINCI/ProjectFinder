package com.akinci.projectfinder.ui.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akinci.projectfinder.core.compose.reduce
import com.akinci.projectfinder.ui.features.splash.SplashViewContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor() : ViewModel() {

    private val _stateFlow: MutableStateFlow<State> = MutableStateFlow(State())
    val stateFlow = _stateFlow.asStateFlow()

    init {
        simulateInitialization()
    }

    private fun simulateInitialization() {
        viewModelScope.launch {
            delay(3000L)

            // complete Splash lottie animation
            _stateFlow.reduce {
                copy(completed = true)
            }
        }
    }

}