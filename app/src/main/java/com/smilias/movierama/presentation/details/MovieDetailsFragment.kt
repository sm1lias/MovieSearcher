package com.smilias.movierama.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.smilias.movierama.R
import com.smilias.movierama.databinding.FragmentMovieDetailsBinding
import com.smilias.movierama.presentation.common.BaseFragment
import com.smilias.movierama.presentation.common.setRating
import com.smilias.movierama.util.Constants
import com.smilias.movierama.util.Util.toMyFormattedString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {
    private val viewModel: DetailsScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.state.collect { state -> handle(state) }
        }

        lifecycleScope.launch {
            viewModel.favoritesMovies.collect { favorites ->
                updateFavoriteIcon(favorites)
            }
        }

        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.favorite.setOnClickListener {
            viewModel.state.value.movie?.let {
                viewModel.onEvent(DetailsScreenEvent.OnFavoriteClick(it.id))
            }
        }
    }

    private fun handle(state: DetailsScreenState) {
        state.movie?.let {
            binding.apply {
                imageView.load(Constants.IMAGE_URL_780 + it.backgroundPath)
                title.text = it.title
                it.releaseDate?.let { rlDate ->
                    date.text = rlDate.toMyFormattedString()
                }
                ratingBar.ratingBarLayout.setRating(it.rating / 2)
                it.overview?.let { desc ->
                    descriptionLL.visibility = View.VISIBLE
                    description.text = desc
                }
                it.director?.let { dir ->
                    descriptionLL.visibility = View.VISIBLE
                    director.text = dir
                }
                it.actors?.let { cst ->
                    castLL.visibility = View.VISIBLE
                    cast.text = cst.joinToString(", ")
                }
                it.reviews?.let { rvs ->
                    reviewsLL.visibility = View.VISIBLE
                    author.text = rvs[0].author
                    reviews.text = rvs[0].review
                }
            }
            updateFavoriteIcon(viewModel.favoritesMovies.value)
        }

    }

    private fun updateFavoriteIcon(favorites: Set<String>) {
        viewModel.state.value.movie?.id?.let {
            if (favorites.contains(it.toString())) {
                binding.favorite.setImageResource(R.drawable.favorite)
            } else {
                binding.favorite.setImageResource(R.drawable.unfavorite)
            }
        }
    }
}