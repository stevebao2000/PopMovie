package com.steve.popMovies.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.steve.popMovies.R
import com.steve.popMovies.databinding.FragmentEntryBinding
import com.steve.popMovies.model.MovieListViewModel

class EntryFragment : Fragment() {

    lateinit var binding: FragmentEntryBinding
    lateinit var movieViewModel: MovieListViewModel

    var movieIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieViewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEntryBinding.inflate(inflater, container, false)
        val root = binding.root

//        val movie = movieViewModel.getMovieAt(movieIndex)
//        binding.title.text = movie.title
//        binding.genre.text = movie.genreids[0].toString()
//        binding.score.text = movie.score.toString()
//        binding.year.text = movie.releaseYear.toString()
//        if (movie.thumbnail.length > 1) {
//            Glide.with(requireActivity())
//                .load(movie.thumbnail)
//                .placeholder(R.drawable.loading)
//                .fitCenter()
//                .into(binding.imageView)
//        }
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(index: Int) = EntryFragment().apply {
            movieIndex = index
        }
    }
}