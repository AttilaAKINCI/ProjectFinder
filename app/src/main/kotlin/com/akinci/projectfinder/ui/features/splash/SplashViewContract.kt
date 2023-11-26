package com.akinci.projectfinder.ui.features.splash

import com.akinci.projectfinder.core.compose.UIState

object SplashViewContract {
    data class State(
        val completed: Boolean = false,
    ): UIState
}