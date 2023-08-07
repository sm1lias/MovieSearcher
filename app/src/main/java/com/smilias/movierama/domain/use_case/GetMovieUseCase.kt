package com.smilias.movierama.domain.use_case

import com.smilias.movierama.domain.model.Movie
import com.smilias.movierama.domain.repository.MovieRepository
import com.smilias.movierama.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    operator fun invoke(id: String): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading())
            emit(
                try {
                    movieRepository.getMovie(id)
                } catch (e: Exception) {
                    Resource.Error("Connection error")
                }
            )
        }
    }
}