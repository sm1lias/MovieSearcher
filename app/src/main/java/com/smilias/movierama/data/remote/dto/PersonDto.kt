package com.smilias.movierama.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonDto(
    @SerialName("name")
    val name: String,
    @SerialName("job")
    val job: String? = null
)
