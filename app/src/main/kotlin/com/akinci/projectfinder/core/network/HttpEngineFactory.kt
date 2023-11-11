package com.akinci.projectfinder.core.network

import io.ktor.client.engine.okhttp.OkHttp
import javax.inject.Inject

class HttpEngineFactory @Inject constructor() {

    fun create() = OkHttp.create()
}