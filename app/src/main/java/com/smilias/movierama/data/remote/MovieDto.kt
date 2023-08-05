package com.smilias.movierama.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    val id: Int,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("backdrop_path")
    val backgroundPath: String?,
    @SerialName("release_date")
    val releaseDate: String?,
    val title: String,
    @SerialName("vote_average")
    val rating: Float,
    @SerialName("genres")
    val genres: List<GenreDto>?,
    @SerialName("overview")
    val overview: String?,
    @SerialName("credits")
    val credits: CreditsDto

)
