package com.smilias.movierama.domain.model

import java.time.LocalDate

data class Movie(
    val id: Int,
    val posterPath: String,
    val backgroundPath: String,
    val releaseDate: LocalDate?,
    val title: String,
    val rating: Float,
    val actors: List<Person>?,
    val director: Person?,
    val overview: String,
    val genre: String?,
    val favorite: Boolean = false
)
