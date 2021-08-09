package com.steve.popMovies.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.steve.popMovies.apapter.MovieListAdapter
import com.steve.popMovies.apapter.MovieRVAdapter
import com.steve.popMovies.databinding.FragmentListBinding
import com.steve.popMovies.model.*
import com.steve.popMovies.repository.MovieRepository
import com.steve.popMovies.repository.network.MovieService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListFragment : Fragment() {
    private val TAG = "ListFragment"
    private lateinit var binding: FragmentListBinding
    private lateinit var movieViewModel: MovieEntryViewModel
    private lateinit var movieListAdapter: MovieListAdapter
    private val logourl = "https://image.tmdb.org/t/p/w500/bOFaAXmWWXC3Rbv4u4uM9ZSzRXP.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = MovieViewModelFactory(MovieRepository(MovieService.invoke()))
        movieViewModel = ViewModelProvider(this, factory).get(MovieEntryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        val root = binding.root
//        CoroutineScope(IO).launch {
//            getPopularMovies(pageNum)
//        }
        setupScrollListener()
        initAdapter()
//        binding.rvMovies.layoutManager = LinearLayoutManager(requireContext()) // See same implementation in xml file.

        val query = DEFAULT_QUERY
        movieViewModel.searchMovie(query)
//        initSearch(query)
        return root
    }

    private fun initAdapter() {
        movieListAdapter = MovieListAdapter()
        binding.rvMovies.adapter = movieListAdapter
        binding.rvMovies.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        movieViewModel.movieResult.observe(viewLifecycleOwner) { result ->
            Log.e(TAG, "initAdapter: Observer the list data update...>>> ")
            when(result) {
                is MovieSearchResult.Success -> {
                    movieListAdapter.submitList(result.data)
//                    result.data.map {println(it)}
                    movieListAdapter.notifyDataSetChanged()
                }
                is MovieSearchResult.Error -> {
                    Toast.makeText(requireContext(), "Error on retrive movies", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun initSearch(query: String) {

    }
//    private suspend fun getPopularMovies(page: Int) {
//        pageNum = page
//        val tmdb = MovieService.invoke().getPopularMovies(Constants.popular_value, page)
////        val movies: MutableList<MovieEntry> = tmdb.results.toMutableList()
////        for (movie in movies) {
////            val newId = (pageNum - 1) * 20 + movie.id  // The index are always start from 0 to 20 for each page.
////            movie.id = newId
////        }
//        updateMovieList(tmdb.results)
//        tmdb.results.map {println(it)}
//    }

//    private suspend fun updateMovieList(list: List<MovieEntry>) {
//        withContext(Main) {
//            movieViewModel.addMovies(list)
//            movieRVAdapter.notifyDataSetChanged()
//        }
//    }

    private fun setupScrollListener() {
        val layoutManager = binding.rvMovies.layoutManager as LinearLayoutManager
        binding.rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                Log.e(TAG, "ListAdapter ScrollListener() trigger")
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItemIndex >= lastVisibleItem) return
                lastVisibleItemIndex = lastVisibleItem
                movieViewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
        @JvmStatic
        var pageNum: Int = 1
        private const val DEFAULT_QUERY = Constants.popular_value
        private var lastVisibleItemIndex = 0
    }
}