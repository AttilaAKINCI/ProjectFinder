package com.akinci.projectfinder.ui.features.projects

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.akinci.projectfinder.R
import com.akinci.projectfinder.core.compose.UIModePreviews
import com.akinci.projectfinder.domain.projects.Project
import com.akinci.projectfinder.ui.ds.components.GifImage
import com.akinci.projectfinder.ui.ds.theme.ProjectFinderTheme
import com.akinci.projectfinder.ui.features.destinations.ProjectDetailScreenDestination
import com.akinci.projectfinder.ui.features.projects.ProjectListViewContract.State
import com.akinci.projectfinder.ui.navigation.MainNavGraph
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.collections.immutable.PersistentList

@MainNavGraph(start = true)
@Destination
@Composable
fun ProjectListScreen(
    navigator: DestinationsNavigator,
    vm: ProjectListViewModel = hiltViewModel(),
) {
    val uiState: State by vm.stateFlow.collectAsStateWithLifecycle()

    ProjectListScreenContent(
        uiState = uiState,
        openProjectDetail = { navigator.navigate(ProjectDetailScreenDestination) },
        updateSearchValue = { vm.updateSearchValue(it) },
        onSearchClick = { vm.search() }
    )
}

@Composable
private fun ProjectListScreenContent(
    uiState: State,
    updateSearchValue: (String) -> Unit,
    onSearchClick: () -> Unit,
    openProjectDetail: () -> Unit,
) {
    Surface {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                ProjectListScreen.Search(
                    searchText = uiState.searchText,
                    updateSearchValue = updateSearchValue,
                    isSearchTextInvalid = uiState.isSearchTextInvalid,
                    onSearchClick = onSearchClick,
                )

                Spacer(modifier = Modifier.height(16.dp))

                when {
                    uiState.isServiceError -> ProjectListScreen.ServiceError()
                    uiState.isNoData -> ProjectListScreen.NoData()
                    uiState.isLoading -> ProjectListScreen.Loading()
                    else -> ProjectListScreen.ProjectList(
                        repositories = uiState.repositories,
                        openProjectDetail = openProjectDetail,
                    )
                }
            }
        }
    }
}

typealias ProjectListScreen = Unit

@Composable
private fun ProjectListScreen.Search(
    searchText: String,
    updateSearchValue: (String) -> Unit,
    isSearchTextInvalid: Boolean,
    onSearchClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Row {
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp, bottom = 8.dp),
            value = searchText,
            onValueChange = { updateSearchValue(it) },
            singleLine = true,
            label = {
                Text(
                    text = stringResource(id = R.string.project_list_repository_owner),
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
            isError = isSearchTextInvalid,
            supportingText = {
                if (isSearchTextInvalid) {
                    Text(
                        text = stringResource(
                            id = R.string.project_list_empty_search_text
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            },
            trailingIcon = {
                if (searchText.isNotBlank()) {
                    IconButton(onClick = { updateSearchValue("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                        )
                    }
                }
            },
        )

        Button(
            modifier = Modifier.padding(top = 12.dp),
            onClick = {
                focusManager.clearFocus()
                onSearchClick()
            }
        ) {
            Text(
                text = stringResource(id = R.string.project_list_search),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun ProjectListScreen.ProjectList(
    repositories: PersistentList<Project>,
    openProjectDetail: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(repositories) {
            Row(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(MaterialTheme.colorScheme.secondary)
                    .clickable(
                        indication = rememberRipple(),
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = openProjectDetail
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 16.dp)
                        .padding(start = 8.dp),
                    text = it.name,
                    style = MaterialTheme.typography.bodyMedium,
                )

                Icon(
                    modifier = Modifier.padding(end = 8.dp),
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
private fun ProjectListScreen.NoData() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp),
        contentAlignment = Alignment.Center,
    ){
        Column {
            GifImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.6f)
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp,
                    )
                    .clip(MaterialTheme.shapes.large),
                gifId = R.drawable.no_data,
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                text = stringResource(id = R.string.project_list_no_data),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun ProjectListScreen.Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp),
        contentAlignment = Alignment.Center,
    ){
        CircularProgressIndicator()
    }
}

@Composable
private fun ProjectListScreen.ServiceError() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp),
        contentAlignment = Alignment.Center,
    ){
        Column {
            GifImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.6f)
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp,
                    )
                    .clip(MaterialTheme.shapes.large),
                gifId = R.drawable.service_error,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                text = stringResource(id = R.string.project_list_service_error),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@UIModePreviews
@Composable
fun ProjectListScreenPreview() {
    ProjectFinderTheme {
        ProjectListScreenContent(
            uiState = State(searchText = ""),
            openProjectDetail = {},
            updateSearchValue = {},
            onSearchClick = {},
        )
    }
}

