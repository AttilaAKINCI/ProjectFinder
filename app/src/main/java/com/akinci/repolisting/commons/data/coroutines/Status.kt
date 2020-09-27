package com.akinci.repolisting.commons.data.coroutines

/** Keeps network request status during kotlin coroutine calls **/
enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}