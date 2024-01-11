package com.panasetskaia.hedvigtestgithubapi.presentation.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.panasetskaia.hedvigtestgithubapi.presentation.MainViewModel
import com.panasetskaia.hedvigtestgithubapi.presentation.navigation.NavGraph
import com.panasetskaia.hedvigtestgithubapi.presentation.navigation.rememberNavigationState
import com.panasetskaia.hedvigtestgithubapi.presentation.screenSlashesInRepoName

@Composable
fun MainAppScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues
) {
    val navigationState = rememberNavigationState()
    Scaffold(
        modifier = Modifier.padding(paddingValues)
    ) { newPaddingValues ->
        NavGraph(
            navigationState = navigationState,
            homeScreenContent = {
                SearchScreen(viewModel = viewModel, paddingValues = newPaddingValues) {
                    viewModel.loadDetails(it)
                    navigationState.navigateToDetailsScreen(screenSlashesInRepoName(it.title ?: "") )
                }
            },
            detailsScreenContent = {
                DetailsScreen(
                    viewModel = viewModel, paddingValues = newPaddingValues,
                    repoName = it
                ) {
                    navigationState.navHostController.popBackStack()
                }
            }
        )
    }
}

