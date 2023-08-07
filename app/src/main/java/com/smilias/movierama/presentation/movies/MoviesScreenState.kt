package com.smilias.movierama.presentation.movies

import androidx.paging.PagingData
import com.smilias.movierama.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MoviesScreenState(
    val movieList: Flow<PagingData<Movie>> = emptyFlow(),
    val searchText: String= "",
    val favoriteMovies: Set<String> = emptySet(),
    )
