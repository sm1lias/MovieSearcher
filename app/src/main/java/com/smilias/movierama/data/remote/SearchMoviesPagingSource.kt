package com.smilias.movierama.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.smilias.movierama.data.mapper.toMovie
import com.smilias.movierama.domain.model.Movie
import com.smilias.movierama.util.Constants
import okio.IOException
import retrofit2.HttpException

class SearchMoviesPagingSource(
    private val movieApi: MovieApi,
    private val query: String
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            // This loads starting from previous page, but since PagingConfig.initialLoadSize spans
            // multiple pages, the initial load will still load items centered around
            // anchorPosition. This also prevents needing to immediately launch prepend due to
            // prefetchDistance.
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: Constants.STARTING_PAGE
        return try {
            val response = movieApi.searchMovies(query, position)
            val moviesDto = response.movies
            val nextKey = if (moviesDto.isEmpty()) {
                null
            } else {
                position + (params.loadSize / Constants.NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = moviesDto.map { it.toMovie() },
                prevKey = if (position == Constants.STARTING_PAGE) null else position - 1,
                nextKey = nextKey
            )

        } catch (e: IOException) {
            LoadResult.Error(Exception("No internet connection"))
        } catch (e: HttpException) {
            LoadResult.Error(Exception("Network error, please try again later"))
        }
    }
}