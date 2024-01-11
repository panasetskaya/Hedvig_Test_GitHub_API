package com.panasetskaia.hedvigtestgithubapi.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation

@Composable
fun NavGraph(
    navigationState: NavigationState,
    homeScreenContent: @Composable () -> Unit,
    detailsScreenContent: @Composable (repoName: String) -> Unit
) {
    NavHost(
        navController = navigationState.navHostController,
        startDestination = NavDestination.Root.route
    ) {
        homeNavGraph(
            homeScreenContent,
            detailsScreenContent
        )
    }
}

fun NavGraphBuilder.homeNavGraph(
    homeScreenContent: @Composable () -> Unit,
    detailsScreenContent: @Composable (repoName: String) -> Unit
) {
    navigation(
        startDestination = NavDestination.Home.route,
        route = NavDestination.Root.route
    ) {
        composable(
            route = NavDestination.Home.route
        ) {
            homeScreenContent()
        }
        composable(
            route = NavDestination.Details.route,
            arguments = listOf(
                navArgument(name = NavDestination.Details.ARG_REPO_NAME, builder = {
                    type = NavType.StringType
                    nullable = true
                })
            )) {
            val repoName =
                it.arguments?.getString(NavDestination.Details.ARG_REPO_NAME) ?: ""
            detailsScreenContent(repoName)
        }
    }
}