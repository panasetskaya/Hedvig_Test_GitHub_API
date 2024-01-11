package com.panasetskaia.hedvigtestgithubapi.presentation.navigation


sealed class NavDestination(
    val route: String
) {
    data object Root : NavDestination(ROUTE_ROOT)
    data object Home : NavDestination(ROUTE_HOME)
    data object Details: NavDestination(ROUTE_DETAILS) {
        private const val ROUTE_FOR_ARGS = "details"

        const val ARG_REPO_NAME = "repo_name"

        fun getRouteWithArgs(repoName: String): String {
            return "$ROUTE_FOR_ARGS/$repoName"
        }
    }

    private companion object {
        const val ROUTE_HOME = "home"
        const val ROUTE_DETAILS = "details/{repo_name}"
        const val ROUTE_ROOT = "root"
    }
}