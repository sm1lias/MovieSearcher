package com.smilias.movierama.presentation.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.smilias.movierama.domain.model.Movie

@Composable
internal fun MoviesRoute(
    onMovieClick: (Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: MoviesScreenViewModel = hiltViewModel()
) {
    val movies = viewModel.moviePagingFlow.collectAsLazyPagingItems()
    val text by viewModel.searchText.collectAsState()
    val favoriteMovies by viewModel.favoritesMovies.collectAsState()
    MoviesScreen(
        onMovieClick = onMovieClick,
        onShowSnackbar = onShowSnackbar,
        onSearchValueChange = viewModel::onSearchTextChange,
        onFavoriteClick = viewModel::onFavoriteClick,
        favoriteMovies = favoriteMovies,
        modifier = modifier,
        movies = movies,
        text = text
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MoviesScreen(
    onMovieClick: (Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<Movie>,
    text: String,
    onSearchValueChange: (String) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    favoriteMovies: Set<String>
) {
    LaunchedEffect(key1 = movies.loadState) {
        if (movies.loadState.refresh is LoadState.Error) {
            val message = (movies.loadState.refresh as LoadState.Error).error.message
            message?.let {
                onShowSnackbar(it, null)
            }

        }
    }

    Box(modifier = modifier.fillMaxSize()) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                query = text,
                onQueryChange = onSearchValueChange,
                onSearch = {},
                active = false,
                onActiveChange = {},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = "Search icon"
                    )
                },
                placeholder = { Text(text = "Search movie") }
            ) {}

//            TextField(
//                value = text,
//                onValueChange = onSearchValueChange,
//                modifier = Modifier.fillMaxWidth(),
//                placeholder = { Text(text = "Search movie") }
//            )
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(movies.itemCount) { index ->
                    val movie = movies[index]
                    if (movie != null) {
                        MovieItem(movie = movie, onMovieClick, onFavoriteClick, favoriteMovies)
                    }

                }
                if (movies.loadState.refresh is LoadState.Loading) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

            }


        }
    }
}