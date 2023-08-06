package com.smilias.movierama.domain.repository

import androidx.paging.PagingData
import com.smilias.movierama.domain.model.Movie
import com.smilias.movierama.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getPopularMovies(): Flow<PagingData<Movie>>

    fun searchMovies(query: String): Flow<PagingData<Movie>>

    suspend fun getMovie(id: String): Resource<Movie>
}