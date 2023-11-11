package com.akinci.projectfinder.ui.features.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.akinci.projectfinder.core.compose.UIModePreviews
import com.akinci.projectfinder.ui.ds.theme.ProjectFinderTheme
import com.akinci.projectfinder.ui.navigation.MainNavGraph
import com.akinci.projectfinder.ui.navigation.animation.SlideInOutHorizontally
import com.ramcosta.composedestinations.annotation.Destination

@MainNavGraph(start = false)
@Destination(style = SlideInOutHorizontally::class)
@Composable
fun ProjectDetailScreen() {

    ProjectListDetailContent()
}

@Composable
private fun ProjectListDetailContent(

) {
    Surface {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Red)
                .windowInsetsPadding(WindowInsets.systemBars)
        ) {
            Text(
                text = "Project Detail"
            )
        }
    }
}

@UIModePreviews
@Composable
fun ProjectListDetailPreview() {
    ProjectFinderTheme {
        ProjectListDetailContent()
    }
}