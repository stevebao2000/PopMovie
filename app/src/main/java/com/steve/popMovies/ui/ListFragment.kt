package com.steve.popMovies.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.steve.popMovies.apapter.MovieAdapter
import com.steve.popMovies.databinding.FragmentListBinding
import com.steve.popMovies.model.Constants
import com.steve.popMovies.model.MovieEntry
import com.steve.popMovies.model.MovieListViewModel
import com.steve.popMovies.repository.network.MovieService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var movieViewModel: MovieListViewModel
    lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieViewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        val root = binding.root
        CoroutineScope(IO).launch {
            getPopularMovies()
        }

        val movieAdapter = MovieAdapter(requireContext(), movieViewModel.list)
        binding.rvMovies.adapter = movieAdapter

        return root
    }

    private suspend fun getPopularMovies() {
        val list = MovieService.invoke().getPopularMovies(Constants.popular_value)
        updateMovieList(list)
    }

    private suspend fun updateMovieList(list: List<MovieEntry>) {
        withContext(Main) {
            movieViewModel.addMovies(list)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}