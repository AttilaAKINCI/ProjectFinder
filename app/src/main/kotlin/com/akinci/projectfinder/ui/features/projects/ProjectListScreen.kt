package com.akinci.projectfinder.ui.features.projects

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.akinci.projectfinder.core.compose.UIModePreviews
import com.akinci.projectfinder.ui.ds.theme.ProjectFinderTheme
import com.akinci.projectfinder.ui.features.destinations.ProjectDetailScreenDestination
import com.akinci.projectfinder.ui.navigation.MainNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@MainNavGraph(start = true)
@Destination
@Composable
fun ProjectListScreen(
    navigator: DestinationsNavigator,
) {
    ProjectListScreenContent(
        openProjectDetail = { navigator.navigate(ProjectDetailScreenDestination) }
    )
}

@Composable
private fun ProjectListScreenContent(
    openProjectDetail: () -> Unit,
) {
    Surface {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Cyan)
                .windowInsetsPadding(WindowInsets.systemBars)
        ) {
            Column {
                Text(text = "Project List")
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = openProjectDetail) {
                    Text(text = "Go to Project Detail")
                }
            }
        }
    }
}

@UIModePreviews
@Composable
fun ProjectListScreenPreview() {
    ProjectFinderTheme {
        ProjectListScreenContent(
            openProjectDetail = {},
        )
    }
}

