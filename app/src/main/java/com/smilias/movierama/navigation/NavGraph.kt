package com.smilias.movierama.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.smilias.movierama.presentation.details.DetailsRoute
import com.smilias.movierama.presentation.movies.MoviesRoute

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    onShowSnackbar: suspend (String, String?) -> Boolean,
) {
    NavHost(navController = navController, startDestination = Screen.MoviesScreen.route) {
        composable(route = Screen.MoviesScreen.route) {
            MoviesRoute(
                onMovieClick = navController::navigateToDetailsScreen,
                onShowSnackbar = onShowSnackbar
            )
        }
        composable(
            route = "${Screen.DetailsScreen.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            DetailsRoute(onShowSnackbar = onShowSnackbar,
                onBackPressed = navController::popBackStack,
                onSimilarMovieClick = navController::navigateToDetailsScreen)
        }
    }
}