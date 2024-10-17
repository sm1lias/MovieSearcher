package com.smilias.movierama.presentation.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.smilias.movierama.databinding.MovieItemBinding
import com.smilias.movierama.domain.model.Movie
import com.smilias.movierama.util.Constants.IMAGE_URL_780

class MoviesAdapter(private val onItemClick: (Int) -> Unit): PagingDataAdapter<Movie, MoviesAdapter.MoviesViewHolder>(REPO_COMPARATOR) {

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
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return MoviesViewHolder(binding) {
            onItemClick(it)
        }
    }

    class MoviesViewHolder(
        private val binding: MovieItemBinding,
        onItemClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }

        fun bind(movie: Movie) {

            binding.apply {
                name.text = movie.title
                imageView.load(IMAGE_URL_780 + movie.backgroundPath)
            }
        }

    }
}