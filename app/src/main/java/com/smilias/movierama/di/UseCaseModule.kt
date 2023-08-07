package com.smilias.movierama.di

import com.smilias.movierama.domain.repository.MovieRepository
import com.smilias.movierama.domain.use_case.GetMovieUseCase
import com.smilias.movierama.domain.use_case.GetPopularMoviesUseCase
import com.smilias.movierama.domain.use_case.SearchMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetMovieUseCase(movieRepository: MovieRepository): GetMovieUseCase {
        return GetMovieUseCase(movieRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideGetPopularMoviesUseCase(movieRepository: MovieRepository): GetPopularMoviesUseCase {
        return GetPopularMoviesUseCase(movieRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSearchMoviesUseCase(movieRepository: MovieRepository): SearchMoviesUseCase {
        return SearchMoviesUseCase(movieRepository)
    }
}