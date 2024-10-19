@file:OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)

package com.smilias.movierama.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.smilias.movierama.domain.preferences.MyPreferences
import com.smilias.movierama.domain.use_case.GetPopularMoviesUseCase
import com.smilias.movierama.domain.use_case.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesScreenViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val prefs: MyPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(MoviesScreenState())
    val state: StateFlow<MoviesScreenState> = _state
    private var job: Job? = null


    init {
        viewModelScope.launch {
            prefs.getFavorites()
                .collect { list ->
                    _state.value = state.value.copy(favoriteMovies = list)
                }
        }
        onSearchTextChange("")
    }

    private fun onSearchTextChange(text: String) {
        _state.value = state.value.copy(searchText = text)
        job?.cancel()
        job = viewModelScope.launch {
            delay(300)
            if (text.isBlank()) {
                _state.value =
                    state.value.copy(movieList = getPopularMoviesUseCase().cachedIn(viewModelScope))
            } else {
                _state.value =
                    state.value.copy(movieList = searchMoviesUseCase(text).cachedIn(viewModelScope))
            }
        }
    }

    fun onEvent(event: MoviesScreenEvent) {
        when (event) {
            MoviesScreenEvent.OnClearClick -> onClearClick()
            is MoviesScreenEvent.OnFavoriteClick -> onFavoriteClick(event.id)
            is MoviesScreenEvent.OnSearchTextChange -> onSearchTextChange(event.text)
        }
    }

    private fun onClearClick() {
        _state.value = _state.value.copy(searchText = "")
    }

    private fun onFavoriteClick(id: Int) {
        val set: Set<String> = if (_state.value.favoriteMovies.contains(id.toString())) {
            _state.value.favoriteMovies.minus(id.toString())
        } else {
            _state.value.favoriteMovies.plus(id.toString())
        }
        viewModelScope.launch { prefs.saveFavorites(set) }
    }
}

sealed class MoviesScreenEvent {
    object OnClearClick : MoviesScreenEvent()
    data class OnFavoriteClick(val id: Int) : MoviesScreenEvent()
    data class OnSearchTextChange(val text: String) : MoviesScreenEvent()
}