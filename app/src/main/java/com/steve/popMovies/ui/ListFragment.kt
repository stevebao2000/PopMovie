package com.steve.popMovies.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.steve.popMovies.R
import com.steve.popMovies.apapter.MovieAdapter
import com.steve.popMovies.databinding.FragmentListBinding
import com.steve.popMovies.model.Constants
import com.steve.popMovies.model.GlideApp
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
    private lateinit var movieAdapter: MovieAdapter
    private val logourl = "https://image.tmdb.org/t/p/w500/bOFaAXmWWXC3Rbv4u4uM9ZSzRXP.jpg"

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
            getPopularMovies(pageNum)
        }
//
//        GlideApp.with(requireActivity())
//            .load(logourl)
//            .error(R.drawable.error)
//            .into(binding.logo)

        movieAdapter = MovieAdapter(requireContext(), movieViewModel.list)
        binding.rvMovies.adapter = movieAdapter
//        binding.rvMovies.layoutManager = LinearLayoutManager(requireContext())
        return root
    }

    private suspend fun getPopularMovies(page: Int) {
        pageNum = page
        val tmdb = MovieService.invoke().getPopularMovies(Constants.popular_value, page)
        updateMovieList(tmdb.results)
        tmdb.results.map {println(it)}
    }

    private suspend fun updateMovieList(list: List<MovieEntry>) {
        withContext(Main) {
            movieViewModel.addMovies(list)
            movieAdapter.notifyDataSetChanged()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
        @JvmStatic
        var pageNum: Int = 1
    }
}