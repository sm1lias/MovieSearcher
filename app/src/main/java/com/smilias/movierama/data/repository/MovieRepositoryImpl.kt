package com.smilias.movierama.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.smilias.movierama.data.remote.MovieApi
import com.smilias.movierama.data.remote.PopularMoviesPagingSource
import com.smilias.movierama.data.remote.SearchMoviesPagingSource
import com.smilias.movierama.domain.model.Movie
import com.smilias.movierama.domain.repository.MovieRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

@OptIn(FlowPreview::class)
class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
): MovieRepository {


    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { PopularMoviesPagingSource(movieApi) }
        ).flow
    }

    override fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { SearchMoviesPagingSource(movieApi, query)}
        ).flow
    }
}