package com.smilias.movierama.presentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.smilias.movierama.R
import com.smilias.movierama.domain.model.Movie
import com.smilias.movierama.presentation.common.RatingBar
import com.smilias.movierama.util.Util.toLowercaseAndCapitalize

@Composable
internal fun DetailsRoute(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    state.value.movie?.let { movie ->


        DetailsScreen(
            onShowSnackbar = onShowSnackbar,
            onBackPressed = onBackPressed,
            modifier = modifier,
            movie = movie
        )
    }
}

@Composable
internal fun DetailsScreen(
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBackPressed: () -> Unit,
    movie: Movie,
    modifier: Modifier = Modifier,
) {
    val painter =
        rememberAsyncImagePainter(
            model = "https://image.tmdb.org/t/p/original${movie.backgroundPath}",
            placeholder = rememberAsyncImagePainter(R.drawable.ic_placeholder),
            error = rememberAsyncImagePainter(R.drawable.ic_placeholder),
        )



    Column(modifier = modifier.fillMaxSize()) {
        Box {
            Image(
                painter = painter,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Back arrow",
                tint = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .size(30.dp)
                    .align(Alignment.TopStart)
                    .clickable { onBackPressed() }
            )
            Text(
                text = movie.title,
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RatingAndDate(movie = movie)
            Icon(imageVector = if (true) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                contentDescription = "Favorite icon",
                modifier = Modifier
                    .clickable { })
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
    var movie = Movie(
        id = 9999,
        title = "My movie",
        posterPath = "",
        backgroundPath = "",
        rating = 6.5F
    )
    DetailsScreen(
        onShowSnackbar = { _, _ -> false },
        onBackPressed = {},
        movie = movie
    )
}