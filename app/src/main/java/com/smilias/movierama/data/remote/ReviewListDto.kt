package com.smilias.movierama.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewListDto(
    @SerialName("results")
    val results: List<ReviewDto>
)
