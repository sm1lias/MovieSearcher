package com.smilias.movierama.presentation.movies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.smilias.movierama.domain.model.Movie
import com.smilias.movierama.domain.preferences.MyPreferences
import com.smilias.movierama.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.switchMap
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class MoviesScreenViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val prefs: MyPreferences
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    val favoritesMovies = prefs.getFavorites()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptySet()
        )


    val moviePagingFlow = _searchText.flatMapLatest { text ->
        if (text.isBlank()) {
            movieRepository.getPopularMovies()
        } else {
            movieRepository.searchMovies(text)
        }
    }.cachedIn(viewModelScope)

    fun onSearchTextChange(text: String) {
//        viewModelScope.launch {
        _searchText.value = text
//        }
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