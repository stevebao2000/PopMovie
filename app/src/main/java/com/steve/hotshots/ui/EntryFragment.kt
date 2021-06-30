package com.steve.hotshots.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.steve.hotshots.R
import com.steve.hotshots.databinding.FragmentListBinding
import com.steve.hotshots.model.MovieListViewModel
import kotlinx.android.synthetic.main.fragment_entry.*

class EntryFragment : Fragment() {

    lateinit var binding: FragmentListBinding
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
        binding = FragmentListBinding.inflate(inflater, container, false)
        val root = binding.root

        val movie = movieViewModel.list.get(movieIndex)
        title.text = movie.title
        genre.text = movie.genreName
        score.text = movie.score.toString()
        year.text = movie.releaseYear.toString()
        Glide.with(requireActivity())
            .load(movie.thumbnail)
            .placeholder(R.drawable.loading)
            .fitCenter()
            .into(imageView)

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(index: Int) = EntryFragment().apply {
            movieIndex = index
        }
    }
}