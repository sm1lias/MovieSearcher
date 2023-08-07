package com.smilias.movierama.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smilias.movierama.domain.preferences.MyPreferences
import com.smilias.movierama.domain.use_case.GetMovieUseCase
import com.smilias.movierama.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getMovieUseCase: GetMovieUseCase,
    private val prefs: MyPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsScreenState())
    val state: StateFlow<DetailsScreenState> = _state

    val favoritesMovies = prefs.getFavorites()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptySet()
        )

    init {
        val id: Int? = savedStateHandle["id"]
        id?.let {
            viewModelScope.launch {
                getMovieUseCase(id.toString()).collect { movieResource ->
                    when (movieResource) {
                        is Resource.Error -> _state.value =
                            DetailsScreenState(error = movieResource.message)

                        is Resource.Loading -> _state.value = DetailsScreenState(isLoading = true)
                        is Resource.Success -> _state.value = DetailsScreenState(movieResource.data)
                    }
                }
            }
        }
    }

    fun onFavoriteClick(id: Int) {
        val set: Set<String> = if (favoritesMovies.value.contains(id.toString())) {
            favoritesMovies.value.minus(id.toString())
        } else {
            favoritesMovies.value.plus(id.toString())
        }
        viewModelScope.launch { prefs.saveFavorites(set) }
    }
}