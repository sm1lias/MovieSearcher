package com.smilias.movierama.data.mapper

import com.smilias.movierama.data.remote.MovieDto
import com.smilias.movierama.domain.model.Movie
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        posterPath = posterPath ?: "",
        backgroundPath = backgroundPath ?: "",
        releaseDate = try {
            LocalDate.parse(releaseDate)
        } catch (e: DateTimeParseException) {
            null
        },
        title = title,
        rating = rating
    )
}