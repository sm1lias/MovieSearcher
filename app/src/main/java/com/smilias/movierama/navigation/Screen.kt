package com.smilias.movierama.navigation

sealed class Screen(val route: String){
    object MoviesScreen: Screen("movies_screen")
    object DetailsScreen: Screen("details_screen")
}
