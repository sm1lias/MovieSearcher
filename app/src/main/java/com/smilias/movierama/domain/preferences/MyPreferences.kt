package com.smilias.movierama.domain.preferences

import kotlinx.coroutines.flow.Flow

interface MyPreferences {

    fun getFavorites(): Flow<Set<String>>

    suspend fun saveFavorites(list: Set<String>)

    companion object {
        const val KEY_FAVORITE = "favorite"
    }
}