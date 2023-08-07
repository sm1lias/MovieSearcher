package com.smilias.movierama.presentation.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.smilias.movierama.R
import com.smilias.movierama.domain.model.Movie
import com.smilias.movierama.domain.model.Review
import com.smilias.movierama.presentation.common.RatingBar
import com.smilias.movierama.ui.theme.LocalSpacing
import com.smilias.movierama.util.Constants
import com.smilias.movierama.util.Constants.IMAGE_URL_500
import com.smilias.movierama.util.Util.toLowercaseAndCapitalize

@Composable
internal fun DetailsRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBackPressed: () -> Unit,
    onSimilarMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val favoriteMovies by viewModel.favoritesMovies.collectAsState()
    LaunchedEffect(key1 = state.error) {
        state.error?.let { message ->
            onShowSnackbar(message, null)
        }
    }
    state.movie?.let {
        DetailsScreen(
            onShowSnackbar = onShowSnackbar,
            onBackPressed = onBackPressed,
            onFavoriteClick = viewModel::onFavoriteClick,
            onSimilarMovieClick = onSimilarMovieClick,
            modifier = modifier,
            favoriteMovies = favoriteMovies,
            state = state
        )
    }
}

@Composable
internal fun DetailsScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBackPressed: () -> Unit,
    onFavoriteClick: (Int) -> Unit,
    onSimilarMovieClick: (Int) -> Unit,
    favoriteMovies: Set<String>,
    state: DetailsScreenState,
    modifier: Modifier = Modifier,
) {

    val dimens = LocalSpacing.current

    Box(modifier = modifier.fillMaxSize()) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        Column(modifier = modifier.verticalScroll(rememberScrollState())) {
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(
                        LocalContext.current
                    ).data(Constants.IMAGE_URL_780 + state.movie!!.backgroundPath)
                        .crossfade(1000)
                        .build(),
                    contentScale = ContentScale.FillWidth,
                    error = painterResource(R.drawable.ic_placeholder),
                    contentDescription = state.movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Back arrow",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(dimens.spaceMedium)
                        .size(30.dp)
                        .align(Alignment.TopStart)
                        .clickable { onBackPressed() }
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .padding(dimens.spaceMedium)
                ) {
                    Text(
                        text = state.movie.title,
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    state.movie.genre?.let { genre ->
                        Text(
                            text = genre,
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }

            }
            Column(modifier = Modifier.padding(dimens.spaceMedium)) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RatingAndDate(movie = state.movie!!)
                    Icon(
                        imageVector = if (favoriteMovies.contains(state.movie.id.toString())) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                        contentDescription = "Favorite icon",
                        modifier = Modifier
                            .clickable { onFavoriteClick(state.movie.id) },
                        tint = if (favoriteMovies.contains(state.movie.id.toString())) Color.Red else Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(dimens.spaceMedium))

                state.movie?.apply {
                    overview?.let { overview ->
                        Text(
                            text = stringResource(id = R.string.description),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(text = overview)
                        Spacer(modifier = Modifier.height(dimens.spaceMedium))
                    }

                    director?.let { director ->
                        Text(
                            text = stringResource(id = R.string.director),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(text = director)
                        Spacer(modifier = Modifier.height(dimens.spaceMedium))
                    }

                    actors?.let { actors ->
                        Text(
                            text = stringResource(id = R.string.cast),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(text = actors.joinToString())

                        Spacer(modifier = Modifier.height(dimens.spaceMedium))

                    }

                    similarMovies?.let { similarMovies ->
                        Text(
                            text = stringResource(id = R.string.similar_movies),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(dimens.spaceSmall),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            items(similarMovies.size) { index ->
                                val movie = similarMovies[index]
                                Card(
                                    modifier = modifier,
                                    elevation = CardDefaults.cardElevation(4.dp)
                                ) {
                                    AsyncImage(
                                        modifier = Modifier
                                            .wrapContentHeight()
                                            .clickable { onSimilarMovieClick(movie.id) },
                                        model = IMAGE_URL_500 + movie.posterPath,
                                        contentScale = ContentScale.FillHeight,
                                        placeholder = painterResource(R.drawable.ic_placeholder),
                                        error = painterResource(R.drawable.ic_placeholder),
                                        contentDescription = movie.title
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(dimens.spaceMedium))
                    }

                    reviews?.let { reviews ->
                        Text(
                            text = stringResource(id = R.string.reviews),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        reviews.forEach { review ->
                            Text(text = review.author, color = Color.Gray)
                            Text(text = review.review)
                            Spacer(modifier = Modifier.height(dimens.spaceMedium))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RatingAndDate(movie: Movie, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier) {
        movie.releaseDate?.let { date ->
            Text(
                text = "${date.dayOfMonth} ${
                    date.month.toString().toLowercaseAndCapitalize()
                } ${date.year}", color = Color.Gray
            )
        }
        RatingBar(rating = movie.rating / 2, maxRating = 5)

    }
}


@Preview
@Composable
fun DetailsScreenPreview() {
    val state = DetailsScreenState(
        movie = Movie(
            id = 9999,
            title = "My movie",
            posterPath = "",
            backgroundPath = "",
            rating = 6.5F,
            actors = listOf("Jolie", "Pitt"),
            director = "Tarantino",
            genre = "Action, Adventure",
            reviews = listOf(Review("Google", "Best Movie for a long time")),
            overview = "One time in Hollywood One time in Hollywood One time in Hollywood One time in Hollywood One time in Hollywood "
        )
    )

    DetailsScreen(
        onShowSnackbar = { _, _ -> false },
        onBackPressed = {},
        onFavoriteClick = {},
        onSimilarMovieClick = {},
        favoriteMovies = emptySet(),
        state = state
    )
}
