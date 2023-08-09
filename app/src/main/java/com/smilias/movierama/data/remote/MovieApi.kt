package com.smilias.movierama.data.remote

import com.smilias.movierama.data.remote.dto.MovieDto
import com.smilias.movierama.data.remote.dto.MovieListDto
import com.smilias.movierama.data.remote.dto.ReviewListDto
import com.smilias.movierama.data.remote.dto.VideoListDto
import com.smilias.movierama.util.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
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

    @Headers("Authorization: Bearer $API_KEY")
    @GET("movie/{movie_id}?append_to_response=credits")
    suspend fun getMovieWithCredits(@Path("movie_id") movieId: String): MovieDto

    @Headers("Authorization: Bearer $API_KEY")
    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(@Path("movie_id") movieId: String): MovieListDto

    @Headers("Authorization: Bearer $API_KEY")
    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(@Path("movie_id") movieId: String): ReviewListDto

    @Headers("Authorization: Bearer $API_KEY")
    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(@Path("movie_id") movieId: String): VideoListDto


}