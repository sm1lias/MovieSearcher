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

    init {
        viewModelScope.launch {
            prefs.getFavorites()
                .collect { list ->
                    _state.value = _state.value.copy(favoriteMovies = list)
                }
        }
        onSearchTextChange("")
    }

    fun onSearchTextChange(text: String) {
        _state.value = _state.value.copy(searchText = text)
        viewModelScope.launch {
            if (text.isBlank()) {
                _state.value =
                    _state.value.copy(movieList = getPopularMoviesUseCase().cachedIn(this))
            } else {
                _state.value =
                    _state.value.copy(movieList = searchMoviesUseCase(text).cachedIn(this))
            }
        }
    }

    fun onClearClick(){
        _state.value = _state.value.copy(searchText = "")
        onSearchTextChange("")
    }

    fun onFavoriteClick(id: Int) {
        val set: Set<String> = if (_state.value.favoriteMovies.contains(id.toString())) {
            _state.value.favoriteMovies.minus(id.toString())
        } else {
            _state.value.favoriteMovies.plus(id.toString())
        }
        viewModelScope.launch { prefs.saveFavorites(set) }
    }
}