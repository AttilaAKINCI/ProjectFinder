package com.akinci.projectfinder.ui.features.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.akinci.projectfinder.R
import com.akinci.projectfinder.core.compose.UIModePreviews
import com.akinci.projectfinder.domain.Owner
import com.akinci.projectfinder.domain.Project
import com.akinci.projectfinder.ui.ds.theme.ProjectFinderTheme
import com.akinci.projectfinder.ui.ds.theme.oval
import com.akinci.projectfinder.ui.ds.theme.titleMediumBold
import com.akinci.projectfinder.ui.features.detail.ProjectDetailViewContract.ScreenArgs
import com.akinci.projectfinder.ui.features.detail.ProjectDetailViewContract.State
import com.akinci.projectfinder.ui.navigation.animation.SlideInOutHorizontally
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph
@Destination(
    style = SlideInOutHorizontally::class,
    navArgsDelegate = ScreenArgs::class
)
@Composable
fun ProjectDetailScreen(
    navigator: DestinationsNavigator,
    vm: ProjectDetailViewModel = hiltViewModel(),
) {
    val uiState: State by vm.stateFlow.collectAsStateWithLifecycle()

    ProjectListDetailContent(
        uiState = uiState,
        onBackClick = { navigator.popBackStack() },
        onFavoriteClick = { vm.toggleFavorite(it) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProjectListDetailContent(
    uiState: State,
    onBackClick: () -> Unit,
    onFavoriteClick: (Boolean) -> Unit,
) {
    Surface {
        Scaffold(
            modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.screen_name_project_details),
                            style = MaterialTheme.typography.titleLarge,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                            )
                        }
                    },
                    actions = {
                        Row(
                            modifier = Modifier.padding(end = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            CompositionLocalProvider(
                                LocalMinimumInteractiveComponentEnforcement provides false
                            ) {
                                IconButton(
                                    modifier = Modifier.size(56.dp),
                                    onClick = { onFavoriteClick(uiState.repository.isFavorite) }
                                ) {
                                    if (uiState.repository.isFavorite) {
                                        Icon(
                                            modifier = Modifier.size(28.dp),
                                            imageVector = Icons.Filled.Favorite,
                                            contentDescription = null,
                                            tint = Color.Red,
                                        )
                                    } else {
                                        Icon(
                                            modifier = Modifier.size(28.dp),
                                            imageVector = Icons.Filled.FavoriteBorder,
                                            contentDescription = null,
                                        )
                                    }
                                }
                            }
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(MaterialTheme.shapes.oval),
                            model = uiState.repository.owner.avatarUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = R.drawable.ic_account_circle_24dp),
                            error = painterResource(id = R.drawable.ic_account_circle_24dp),
                        )

                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = uiState.repository.owner.name,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(id = R.string.project_detail_title),
                        style = MaterialTheme.typography.titleMediumBold,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(
                            id = R.string.project_detail_repository_name,
                            uiState.repository.name
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(
                            id = R.string.project_detail_start_count,
                            uiState.repository.starCount
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(
                            id = R.string.project_detail_open_issue_count,
                            uiState.repository.openIssueCount
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    if (!uiState.repository.language.isNullOrBlank()) {
                        Text(
                            text = stringResource(
                                id = R.string.project_detail_language,
                                uiState.repository.language
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    if (!uiState.repository.description.isNullOrBlank()) {
                        Text(
                            text = uiState.repository.description,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@UIModePreviews
@Composable
fun ProjectListDetailPreview() {
    ProjectFinderTheme {
        ProjectListDetailContent(
            uiState = State(
                Project(
                    id = 1000L,
                    name = "Sample repository",
                    url = "",
                    owner = Owner(
                        id = 10,
                        name = "Owner",
                        avatarUrl = "",
                    ),
                    starCount = 1,
                    openIssueCount = 2,
                    language = "kotlin",
                    description = "Sample Description",
                    isFavorite = false,
                )
            ),
            onBackClick = {},
            onFavoriteClick = {},
        )
    }
}