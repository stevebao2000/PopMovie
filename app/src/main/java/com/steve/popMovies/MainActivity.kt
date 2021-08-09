package com.steve.popMovies

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.steve.popMovies.apapter.MovieListAdapter
import com.steve.popMovies.databinding.ActivityMainBinding
import com.steve.popMovies.databinding.FragmentListBinding
import com.steve.popMovies.model.Constants
import com.steve.popMovies.model.MovieEntryViewModel
import com.steve.popMovies.model.MovieSearchResult
import com.steve.popMovies.model.MovieViewModelFactory
import com.steve.popMovies.repository.MovieRepository
import com.steve.popMovies.repository.network.MovieService
import com.steve.popMovies.ui.EntryFragment
import com.steve.popMovies.ui.ListFragment


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    val INTERNET_PERMISSION = 101
    val NETWORK_STATE = 102
//    private lateinit var binding: FragmentListBinding
    private lateinit var movieViewModel: MovieEntryViewModel
    private lateinit var movieListAdapter: MovieListAdapter
    private val logourl = "https://image.tmdb.org/t/p/w500/bOFaAXmWWXC3Rbv4u4uM9ZSzRXP.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkForPermissions(android.Manifest.permission.INTERNET, "Internet", INTERNET_PERMISSION)
        checkForPermissions(android.Manifest.permission.ACCESS_NETWORK_STATE, "Network State", NETWORK_STATE)

        val factory = MovieViewModelFactory(MovieRepository(MovieService.invoke()))
        movieViewModel = ViewModelProvider(this, factory).get(MovieEntryViewModel::class.java)

        setupScrollListener()
        initAdapter()
//        binding.rvMovies.layoutManager = LinearLayoutManager(requireContext()) // See same implementation in xml file.

        val query = Constants.requestPath
        movieViewModel.searchMovie(query)

        // when activity create
//        showListFragment()
    }
//
//    fun showListFragment() {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.container, ListFragment.newInstance())
//            .commitNow()
//    }
//
//    fun showEntryFragment(index: Int) {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.container, EntryFragment.newInstance(index))
//            .commitNow()
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }
    private fun checkForPermissions(permission: String, name: String, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(applicationContext, permission) == PackageManager.PERMISSION_DENIED ->
                    ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            }
        }
    }
    private fun initAdapter() {
        movieListAdapter = MovieListAdapter()
        binding.rvMovies.adapter = movieListAdapter
        binding.rvMovies.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        movieViewModel.movieResult.observe(this) { result ->
            Log.e(TAG, "initAdapter: Observer the list data update...>>> ")
            when(result) {
                is MovieSearchResult.Success -> {
                    movieListAdapter.submitList(result.data)
//                    result.data.map {println(it)}
                    movieListAdapter.notifyDataSetChanged()
                }
                is MovieSearchResult.Error -> {
                    Toast.makeText(this, "Error on retrive movies", Toast.LENGTH_SHORT).show()
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
//                Log.e(TAG, "ListAdapter ScrollListener() trigger")
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