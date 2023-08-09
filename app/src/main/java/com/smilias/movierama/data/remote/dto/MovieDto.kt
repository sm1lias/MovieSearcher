package com.smilias.movierama.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    val id: Int,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("backdrop_path")
    val backgroundPath: String? = null,
    @SerialName("release_date")
    val releaseDate: String?,
    val title: String,
    @SerialName("vote_average")
    val rating: Float,
    @SerialName("genres")
    val genres: List<GenreDto>? = null,
    @SerialName("overview")
    val overview: String? = null,
    @SerialName("credits")
    val credits: CreditsDto? = null,
    @SerialName("videos")
    val videos: VideoListDto? = null

)
