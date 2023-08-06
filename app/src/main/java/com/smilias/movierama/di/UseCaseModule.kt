package com.smilias.movierama.di

import com.smilias.movierama.domain.repository.MovieRepository
import com.smilias.movierama.domain.use_case.GetMovieUseCase
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
}