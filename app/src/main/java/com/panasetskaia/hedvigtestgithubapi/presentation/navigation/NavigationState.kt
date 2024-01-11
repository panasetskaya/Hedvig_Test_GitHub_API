package com.panasetskaia.hedvigtestgithubapi.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavigationState(
    val navHostController: NavHostController
) {
    fun navigateToDetailsScreen(repoName: String) {
        navHostController.navigate(NavDestination.Details.getRouteWithArgs(repoName))
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}
