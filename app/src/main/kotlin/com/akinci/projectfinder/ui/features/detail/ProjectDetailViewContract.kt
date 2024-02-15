package com.akinci.projectfinder.ui.features.detail

import android.os.Parcelable
import com.akinci.projectfinder.domain.data.Project
import kotlinx.parcelize.Parcelize

object ProjectDetailViewContract {

    data class State(
        val repository: Project,
    )

    @Parcelize
    data class ScreenArgs(
        val project: Project,
    ) : Parcelable
}