package com.smilias.movierama.presentation.movies

data class MoviesScreenState(
    val searchText: String = "",
    val favoriteMovies: Set<String>,

)
