package com.akinci.projectfinder.ui.features.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.akinci.projectfinder.R
import com.akinci.projectfinder.core.compose.UIModePreviews
import com.akinci.projectfinder.ui.ds.components.InfiniteLottieAnimation
import com.akinci.projectfinder.ui.ds.theme.ProjectFinderTheme
import com.akinci.projectfinder.ui.features.NavGraphs
import com.akinci.projectfinder.ui.features.destinations.ProjectListScreenDestination
import com.akinci.projectfinder.ui.features.splash.SplashScreenViewContract.State
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator,
    vm: SplashScreenViewModel = hiltViewModel(),
) {
    val uiState: State by vm.stateFlow.collectAsStateWithLifecycle()

    if (uiState.completed) {
        navigator.navigate(ProjectListScreenDestination) {
            popUpTo(NavGraphs.root.route) {
                inclusive = true
            }
        }
    }

    SplashScreenContent()
}

@Composable
fun SplashScreenContent() {
    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                InfiniteLottieAnimation(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .aspectRatio(1f)
                        .testTag("lottie_animation"),
                    animationId = R.raw.anim_splash_animation
                )
                Spacer(modifier = Modifier.height(64.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.app_name),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    }
}

@UIModePreviews
@Composable
fun SplashScreenPreview() {
    ProjectFinderTheme {
        SplashScreenContent()
    }
}