package com.smilias.movierama.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smilias.movierama.data.mapper.toMovie
import com.smilias.movierama.data.remote.dto.MovieListDto
import com.smilias.movierama.domain.model.Movie
import com.smilias.movierama.util.Constants.NETWORK_PAGE_SIZE
import com.smilias.movierama.util.Constants.STARTING_PAGE
import okio.IOException
import retrofit2.HttpException

class PopularMoviesPagingSource(
    private val movieApi: MovieApi
) : MoviesPagingSource() {
    override suspend fun getMovies(position: Int): MovieListDto {
        return movieApi.getPopularMovies(position)
    }


}