package com.smilias.movierama.data.remote

import com.smilias.movierama.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieApi {

    @Headers("Authorization: Bearer $API_KEY")
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): MovieListDto

    @Headers("Authorization: Bearer $API_KEY")
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ): MovieListDto
}