package com.smilias.movierama.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewDto(
    @SerialName("author")
    val author: String,
    @SerialName("content")
    val review: String
)
