package com.smilias.movierama.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoDto(
    @SerialName("key")
    val key: String,
    @SerialName("type")
    val type: String
)
