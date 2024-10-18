package com.smilias.movierama.presentation.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.smilias.movierama.R
import com.smilias.movierama.databinding.MovieItemBinding
import com.smilias.movierama.domain.model.Movie
import com.smilias.movierama.presentation.common.setRating
import com.smilias.movierama.util.Constants.IMAGE_URL_780
import com.smilias.movierama.util.Util.toLowercaseAndCapitalize
import com.smilias.movierama.util.Util.toMyFormattedString
import java.time.LocalDate

class MoviesAdapter(private val onItemClick: (Int) -> Unit,
    private val onFavoriteClick: (Int) -> Unit,
    ): PagingDataAdapter<Movie, MoviesAdapter.MoviesViewHolder>(REPO_COMPARATOR) {

    var favoriteMovies: Set<String> = emptySet()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id
        }
    }


    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, favoriteMovies)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return MoviesViewHolder(binding, {
            onItemClick(it)
        }, {onFavoriteClick(it)})
    }

    class MoviesViewHolder(
        private val binding: MovieItemBinding,
        onItemClicked: (Int) -> Unit,
        onFavoriteClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var movieId: Int? = null

        init {
            binding.imageView.setOnClickListener {
                movieId?.let {
                    onItemClicked(it)
                }
            }
            binding.favorite.setOnClickListener{
                movieId?.let {
                    onFavoriteClick(it)
                }
            }
        }

        fun bind(movie: Movie,favoriteMovies: Set<String>) {
            movieId = movie.id

            binding.apply {
                name.text = movie.title
                imageView.load(data = (IMAGE_URL_780 + movie.backgroundPath))
                ratingBar.ratingBarLayout.setRating(movie.rating / 2)
                setReleaseDate(movie)
                setFavoriteIcon(favoriteMovies, movie)
            }
        }

        private fun MovieItemBinding.setFavoriteIcon(
            favoriteMovies: Set<String>,
            movie: Movie
        ) {
            favorite.setImageResource(
                if (favoriteMovies.contains(movie.id.toString())) {
                    R.drawable.favorite
                } else {
                    R.drawable.unfavorite
                }
            )
        }

        private fun MovieItemBinding.setReleaseDate(movie: Movie) {
            movie.releaseDate?.let { rlDate ->
                date.text = rlDate.toMyFormattedString()
            }
        }

    }
}