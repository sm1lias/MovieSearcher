package com.smilias.movierama.data.mapper

import com.smilias.movierama.data.remote.dto.MovieDto
import com.smilias.movierama.data.remote.dto.MovieListDto
import com.smilias.movierama.data.remote.dto.ReviewListDto
import com.smilias.movierama.domain.model.Movie
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun MovieDto.toMovie(
    reviewListDto: ReviewListDto? = null,
    similarMovies: MovieListDto? = null
): Movie {
    return Movie(
        id = id,
        posterPath = posterPath,
        backgroundPath = backgroundPath,
        releaseDate = try {
            LocalDate.parse(releaseDate)
        } catch (e: DateTimeParseException) {
            null
        },
        title = title,
        rating = rating,
        overview = overview,
        reviews = reviewListDto?.results?.take(2)?.takeIf { it.isNotEmpty() }?.map { it.toReview() },
        actors = credits?.actors?.take(4)?.map { it.name },
        director = credits?.director?.firstOrNull { it.job == "Director" }?.name,
        genre = genres?.joinToString { it.genre },
        similarMovies = similarMovies?.movies?.takeIf { it.isNotEmpty() }?.map { it.toMovie() }
    )
}