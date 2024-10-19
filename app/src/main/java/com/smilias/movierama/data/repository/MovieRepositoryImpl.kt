package com.smilias.movierama.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.smilias.movierama.data.mapper.toMovie
import com.smilias.movierama.data.remote.MovieApi
import com.smilias.movierama.data.remote.PopularMoviesPagingSource
import com.smilias.movierama.data.remote.SearchMoviesPagingSource
import com.smilias.movierama.domain.model.Movie
import com.smilias.movierama.domain.repository.MovieRepository
import com.smilias.movierama.util.Resource
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {


    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = true),
            pagingSourceFactory = { PopularMoviesPagingSource(movieApi) }
        ).flow
    }

    override fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = true),
            pagingSourceFactory = { SearchMoviesPagingSource(movieApi, query) }
        ).flow
    }

    override suspend fun getMovie(id: String): Resource<Movie> {
        return supervisorScope {
            val movieDto = try {
                async { movieApi.getMovieWithCredits(id) }.await()
            } catch (e: Exception) {
                return@supervisorScope Resource.Error("No internet connection")
            }
            val reviewListDto =
                async { fetchWithCatch { movieApi.getMovieReviews(id) } }.await()
            val similarMoviesDto =
                async { fetchWithCatch { movieApi.getSimilarMovies(id) } }.await()
            val videoListDto =
                async { fetchWithCatch { movieApi.getMovieVideos(id) } }.await()

            Resource.Success(movieDto.toMovie(reviewListDto, similarMoviesDto, videoListDto))
        }
    }

    private suspend fun <T> fetchWithCatch(fetch: suspend () -> T): T? {
        return try {
            fetch()
        } catch (e: Exception) {
            null
        }
    }
}