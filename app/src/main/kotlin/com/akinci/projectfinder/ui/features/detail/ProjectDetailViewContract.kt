package com.akinci.projectfinder.ui.features.detail

import android.os.Parcelable
import com.akinci.projectfinder.core.compose.UIState
import com.akinci.projectfinder.domain.Project
import kotlinx.parcelize.Parcelize

object ProjectDetailViewContract {

    data class State(
        val repository: Project,
    ): UIState

    @Parcelize
    data class ScreenArgs(
        val project: Project,
    ) : Parcelable
}