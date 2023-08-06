package com.smilias.movierama.presentation.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.smilias.movierama.domain.model.Movie
import com.smilias.movierama.presentation.common.RatingBar
import com.smilias.movierama.ui.theme.MovieRamaTheme
import com.smilias.movierama.util.Util.toLowercaseAndCapitalize
import java.time.LocalDate

@Composable
fun MovieItem(
    movie: Movie,
    onMovieClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    favoriteMovies: Set<String>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = modifier.fillMaxSize()) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/original${movie.backgroundPath}",
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .clickable { onMovieClick(movie.id) }
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(5f)) {
                    Text(text = movie.title, maxLines = 1)
                    RatingAndDate(movie = movie)
                }
                Spacer(modifier = Modifier.width(4.dp))
                Icon(imageVector = if (favoriteMovies.contains(movie.id.toString())) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                    contentDescription = "Favorite icon",
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onFavoriteClick(movie.id) })
            }
        }
    }

}

@Composable
private fun RatingAndDate (movie: Movie) {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        RatingBar(rating = movie.rating / 2, maxRating = 5)
        movie.releaseDate?.let {date ->
            Text(text = "${date.dayOfMonth} ${date.month.toString().toLowercaseAndCapitalize()} ${date.year}", color = Color.Gray)
        }

    }
}

@Composable
@Preview
fun MovieItemPreview() {
    MovieRamaTheme {
        MovieItem(movie = Movie(
            id = 1,
            posterPath = "/rktDFPbfHfUbArZ6OOOKsXcv0Bm.jpg",
            backgroundPath = "/random",
            releaseDate = LocalDate.now(),
            rating = 7.5F,
            title = "My movie"
        ), {}, {}, emptySet()
        )
    }

}