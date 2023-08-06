package com.smilias.movierama.domain.model

import java.time.LocalDate

data class Movie(
    val id: Int,
    val posterPath: String,
    val backgroundPath: String,
    val releaseDate: LocalDate? = null,
    val title: String,
    val rating: Float,
    val actors: List<String>? = null,
    val director: String? = null,
    val overview: String? = null,
    val reviews: List<Review>? = null,
    val similarMovies: List<Movie>? = null,
    val genre: String? = null,
    val favorite: Boolean = false
)
