package com.smilias.movierama.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreditsDto(
    @SerialName("cast")
    val actors: List<PersonDto>? = null,
    @SerialName("crew")
    val director: List<PersonDto>? = null
)
