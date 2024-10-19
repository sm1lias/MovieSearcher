package com.smilias.movierama.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smilias.movierama.data.mapper.toMovie
import com.smilias.movierama.data.remote.dto.MovieListDto
import com.smilias.movierama.domain.model.Movie
import com.smilias.movierama.util.Constants
import okio.IOException
import retrofit2.HttpException

class SearchMoviesPagingSource(
    private val movieApi: MovieApi,
    private val query: String
) : MoviesPagingSource() {

    override suspend fun getMovies(position: Int): MovieListDto {
        return movieApi.searchMovies(query, position)
    }
}