package com.smilias.movierama.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smilias.movierama.data.mapper.toMovie
import com.smilias.movierama.data.remote.dto.MovieListDto
import com.smilias.movierama.domain.model.Movie
import com.smilias.movierama.util.Constants
import okio.IOException
import retrofit2.HttpException

abstract class MoviesPagingSource: PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: Constants.STARTING_PAGE
        return try {
            val response = getMovies(position)
            val moviesDto = response.movies
            val nextKey = if (moviesDto.isEmpty()) {
                null
            } else {
                position + 1
            }
            val prevKey = if (position == Constants.STARTING_PAGE) null else position - 1
            LoadResult.Page(
                data = moviesDto.map { it.toMovie() },
                prevKey = prevKey,
                nextKey = nextKey
            )

        } catch (e: IOException) {
            LoadResult.Error(Exception("No internet connection"))
        } catch (e: HttpException) {
            LoadResult.Error(Exception("Network error, please try again later"))
        }
    }

    abstract suspend fun getMovies(position: Int): MovieListDto
}