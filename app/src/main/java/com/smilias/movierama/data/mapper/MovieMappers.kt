package com.smilias.movierama.data.mapper

import com.smilias.movierama.data.remote.CreditsDto
import com.smilias.movierama.data.remote.MovieDto
import com.smilias.movierama.data.remote.MovieListDto
import com.smilias.movierama.data.remote.ReviewListDto
import com.smilias.movierama.domain.model.Movie
import java.time.LocalDate
import java.time.format.DateTimeParseException

fun MovieDto.toMovie(
    reviewListDto: ReviewListDto? = null,
    similarMovies: MovieListDto? = null
): Movie {
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
        rating = rating,
        overview = overview,
        reviews = reviewListDto?.results?.take(2)?.map { it.toReview() },
        actors = credits?.actors?.take(4)?.map { it.name },
        director = credits?.director?.first { it.job == "Director" }?.name,
        genre = genres?.joinToString { it.genre },
        similarMovies = similarMovies?.movies?.map { it.toMovie() }
    )
}