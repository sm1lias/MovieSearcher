package com.smilias.movierama.di

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.smilias.movierama.data.remote.MovieApi
import com.smilias.movierama.data.remote.PopularMoviesPagingSource
import com.smilias.movierama.domain.model.Movie
import com.smilias.movierama.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit{
        val contentType = "application/json".toMediaType()
        val json = Json{
            ignoreUnknownKeys = true
        }
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMoviePager(movieApi: MovieApi): Pager<Int, Movie> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {PopularMoviesPagingSource(movieApi)}
        )
    }
}