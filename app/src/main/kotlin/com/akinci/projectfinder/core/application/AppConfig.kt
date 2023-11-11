package com.akinci.projectfinder.core.application

import com.akinci.projectfinder.BuildConfig
import javax.inject.Inject

class AppConfig @Inject constructor() {
    fun isDebugMode() = BuildConfig.DEBUG

    fun getServiceEndpointBaseUrl() = BuildConfig.SERVICE_ENDPOINT_BASE_URL
}