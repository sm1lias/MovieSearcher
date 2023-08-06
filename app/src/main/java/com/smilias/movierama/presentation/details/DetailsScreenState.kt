package com.smilias.movierama.presentation.details

import com.smilias.movierama.domain.model.Movie

data class DetailsScreenState(
    val movie: Movie? = null,
    val isLoading: Boolean = false
)
