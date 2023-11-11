package com.akinci.projectfinder.core.compose

import kotlinx.coroutines.flow.MutableStateFlow

interface UIState

fun <T : UIState> MutableStateFlow<T>.reduce(reducer: T.() -> T) {
    this.value = reducer(this.value)
}