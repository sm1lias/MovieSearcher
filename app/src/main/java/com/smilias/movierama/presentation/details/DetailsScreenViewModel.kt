package com.smilias.movierama.presentation.details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smilias.movierama.domain.preferences.MyPreferences
import com.smilias.movierama.domain.repository.MovieRepository
import com.smilias.movierama.domain.use_case.GetMovieUseCase
import com.smilias.movierama.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
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
       savedStateHandle.get<Int>("id").let {id ->
           viewModelScope.launch {
               getMovieUseCase(id.toString()).collect {movieResource ->
                   when (movieResource) {
                       is Resource.Error -> {}
                       is Resource.Loading -> _state.value = DetailsScreenState(isLoading = true)
                       is Resource.Success -> _state.value = DetailsScreenState(movieResource.data)
                   }
               }

           }
       }
    }

    fun onFavoriteClick(id: Int){
        val set: Set<String> = if (favoritesMovies.value.contains(id.toString())){
            favoritesMovies.value.minus(id.toString())
        } else {
            favoritesMovies.value.plus(id.toString())
        }
        viewModelScope.launch { prefs.saveFavorites(set) }
    }
}