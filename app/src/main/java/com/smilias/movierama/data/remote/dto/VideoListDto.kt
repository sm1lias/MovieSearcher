package com.smilias.movierama.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VideoListDto(
    @SerialName("results")
    val results: List<VideoDto>
)
