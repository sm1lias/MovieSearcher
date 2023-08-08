@file:OptIn(FlowPreview::class)

package com.smilias.movierama.domain.use_case

import androidx.paging.PagingData
import com.smilias.movierama.domain.model.Movie
import com.smilias.movierama.domain.repository.MovieRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    operator fun invoke(): Flow<PagingData<Movie>> {
        return movieRepository.getPopularMovies()
    }
}