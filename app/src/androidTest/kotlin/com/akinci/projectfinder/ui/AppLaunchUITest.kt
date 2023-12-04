package com.akinci.projectfinder.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import com.akinci.projectfinder.ui.features.main.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class AppLaunchUITest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun checkSplashAnimationDisplayed() = runTest {
        // Check lottie animation is displayed
        composeTestRule.onNodeWithTag("lottie_animation").assertIsDisplayed()

        // Wait until automatically navigated to Project List Screen
        composeTestRule.waitUntil(timeoutMillis = 3000L) {
            composeTestRule.onAllNodesWithText("Search").fetchSemanticsNodes().isNotEmpty()
        }
    }
}