package com.steve.hotshots.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.steve.hotshots.R
import com.steve.hotshots.databinding.FragmentListBinding
import com.steve.hotshots.model.MovieListViewModel

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

        // TODO more work on the Entry of movieIndex
        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(index: Int) = EntryFragment().apply {
            movieIndex = index
        }
    }
}