package com.akinci.projectfinder.core.application

import android.app.Application
import com.akinci.projectfinder.core.logger.LoggerInitializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ProjectFinderApp : Application() {
    @Inject
    lateinit var loggerInitializer: LoggerInitializer

    override fun onCreate() {
        super.onCreate()

        // initialize timber trees
        loggerInitializer.initialize()
    }
}