package com.smilias.movierama.domain.model

import kotlinx.serialization.SerialName
import java.time.LocalDate
import java.time.LocalDateTime

data class Movie(
    val id: Int,
    val posterPath: String,
    val backgroundPath: String,
    val releaseDate: LocalDate?,
    val title: String,
    val rating: Float,
    val favorite: Boolean = false
)
