package com.smilias.movierama.data.repository

import app.cash.turbine.turbineScope
import com.smilias.movierama.data.mapper.toMovie
import com.smilias.movierama.data.remote.MovieApi
import com.smilias.movierama.data.remote.dto.MovieDto
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever


class MovieRepositoryImplTest{
    @Mock
    private lateinit var movieApi: MovieApi
    private lateinit var movieRepository: MovieRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        movieRepository = MovieRepositoryImpl(movieApi)
    }

    @Test
    fun `test getMovie happy path`() = runTest{
        val movieDto = MovieDto(1, "Title 1", "Desc 1", title = "title", rating = 4.5F, releaseDate = null)
        val movie = movieDto.toMovie()
        val id = "1"

        turbineScope {
            whenever(movieApi.getMovieWithCredits(id)).thenReturn(movieDto)
            whenever(movieApi.getMovieVideos(id)).thenReturn(null)
            whenever(movieApi.getMovieReviews(id)).thenReturn(null)
            whenever(movieApi.getSimilarMovies(id)).thenReturn(null)
            val value = movieRepository.getMovie(id)
            assertEquals(movie, value.data!!)
        }
    }

    @Test
    fun `test getMovie some api call throw exception`() = runTest{
        val movieDto = MovieDto(1, "Title 1", "Desc 1", title = "title", rating = 4.5F, releaseDate = null)
        val movie = movieDto.toMovie()
        val id = "1"

        turbineScope {
            whenever(movieApi.getMovieWithCredits(id)).thenReturn(movieDto)
            whenever(movieApi.getMovieVideos(id)).thenThrow(RuntimeException())
            whenever(movieApi.getMovieReviews(id)).thenReturn(null)
            whenever(movieApi.getSimilarMovies(id)).thenReturn(null)
            val value = movieRepository.getMovie(id)
            assertEquals(movie, value.data!!)
        }
    }

    @Test
    fun `test getMovie throws exception main api call return error with message`() = runTest{
        val id = "1"
        val errorMessage = "No internet connection"

        turbineScope {
            whenever(movieApi.getMovieWithCredits(id)).thenThrow(RuntimeException())
            whenever(movieApi.getMovieVideos(id)).thenReturn(null)
            whenever(movieApi.getMovieReviews(id)).thenReturn(null)
            whenever(movieApi.getSimilarMovies(id)).thenReturn(null)
            val value = movieRepository.getMovie(id)
            assertEquals(errorMessage, value.message)
        }
    }


    }
