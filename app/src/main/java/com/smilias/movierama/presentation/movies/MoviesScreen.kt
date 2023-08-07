@file:OptIn(ExperimentalMaterialApi::class)

package com.smilias.movierama.presentation.movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.smilias.movierama.ui.theme.LocalSpacing
import kotlinx.coroutines.time.delay
import java.time.Duration

@Composable
internal fun MoviesRoute(
    onMovieClick: (Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    viewModel: MoviesScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

        MoviesScreen(
            onMovieClick = onMovieClick,
            onShowSnackbar = onShowSnackbar,
            onSearchValueChange = viewModel::onSearchTextChange,
            onFavoriteClick = viewModel::onFavoriteClick,
            state = state,
            modifier = modifier,
        )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MoviesScreen(
    onMovieClick: (Int) -> Unit,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    modifier: Modifier = Modifier,
    onSearchValueChange: (String) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    state: MoviesScreenState
) {
    val dimens = LocalSpacing.current
    val movies = state.movieList.collectAsLazyPagingItems()
    LaunchedEffect(key1 = movies.loadState) {
        if (movies.loadState.refresh is LoadState.Error) {
            val message = (movies.loadState.refresh as LoadState.Error).error.message
            message?.let {
                onShowSnackbar(it, null)
            }
        }
        if (movies.loadState.append is LoadState.Error) {
            delay(Duration.ofMillis(2000))
            movies.retry()
        }
    }

    val pullRefreshState =
        rememberPullRefreshState(refreshing = false, onRefresh = { movies.refresh() })

    Box(modifier = modifier
        .fillMaxSize()
        .pullRefresh(pullRefreshState)
        .padding(dimens.spaceSmall)) {
        if (movies.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                query = state.searchText,
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
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(movies.itemCount) { index ->
                    val movie = movies[index]
                    if (movie != null) {
                        MovieItem(movie = movie, onMovieClick, onFavoriteClick, state.favoriteMovies)
                    }

                }
                if (movies.loadState.append is LoadState.Loading) {
                    item{
                        CircularProgressIndicator()
                    }
                }
            }


        }
        PullRefreshIndicator(false, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}