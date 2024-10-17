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

@OptIn(FlowPreview::class)
class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {


    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { PopularMoviesPagingSource(movieApi) }
        ).flow
    }

    override fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { SearchMoviesPagingSource(movieApi, query) }
        ).flow
    }

    override suspend fun getMovie(id: String): Resource<Movie> {
        //movieDto is mandatory is it crashes we want to inform the user
        //but for the other three we show them only if everything go ok
        //also we want to make all api calls in parallel
        return supervisorScope {
            val movieDto = try {
                async { movieApi.getMovieWithCredits(id) }.await()
            } catch (e: Exception) {
                return@supervisorScope Resource.Error("No internet connection")
            }
            val reviewListDto = try {
                async { movieApi.getMovieReviews(id) }.await()
            } catch (e: Exception) {
                null
            }
            val similarMoviesDto = try {
                async { movieApi.getSimilarMovies(id) }.await()
            } catch (e: Exception) {
                null
            }
            val videoListDto = try {
                async { movieApi.getMovieVideos(id) }.await()
            } catch (e: Exception) {
                null
            }

            Resource.Success(movieDto.toMovie(reviewListDto, similarMoviesDto, videoListDto))
        }
    }
}