package com.smilias.movierama.presentation.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.smilias.movierama.R
import com.smilias.movierama.databinding.FragmentMoviesBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var moviesAdapter: MoviesAdapter
    private val viewModel: MoviesScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moviesAdapter = MoviesAdapter(onItemClick = { id ->
            val bundle = Bundle().apply {
                putInt("id", id)
            }
            findNavController().navigate(R.id.action_moviesFragment_to_movieDetailsFragment, bundle)
        }, onFavoriteClick = { id -> viewModel.onEvent(MoviesScreenEvent.OnFavoriteClick(id)) })

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = moviesAdapter
        }

        lifecycleScope.launch {
            viewModel.state.collect { state -> handle(state)
                moviesAdapter.favoriteMovies = state.favoriteMovies}
        }
        observeUI()
    }

    private fun observeUI() {
        binding.searchBar.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.onEvent(MoviesScreenEvent.OnSearchTextChange(newText.orEmpty()))
                return true
            }
        })
    }

    private fun handle(state: MoviesScreenState) {
        lifecycleScope.launch {
            state.movieList.distinctUntilChanged().collectLatest {
                moviesAdapter.submitData(it)
            }
        }
    }
}