package com.akinci.projectfinder.core.network

import io.ktor.client.engine.okhttp.OkHttp
import javax.inject.Inject

/**
 * HttpEngineFactory encapsulation is created to mock network requests for unit tests
 * **/
class HttpEngineFactory @Inject constructor() {

    fun create() = OkHttp.create()
}