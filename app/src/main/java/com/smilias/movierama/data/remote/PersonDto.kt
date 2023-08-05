package com.smilias.movierama.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonDto(
    @SerialName("name")
    val name: String,
    @SerialName("job")
    val job: String?
)
