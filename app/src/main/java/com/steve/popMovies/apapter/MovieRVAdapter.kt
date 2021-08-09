package com.steve.popMovies.apapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.steve.popMovies.MainActivity
import com.steve.popMovies.R
import com.steve.popMovies.databinding.MovieItemBinding
import com.steve.popMovies.model.Constants
import com.steve.popMovies.model.GlideApp
import com.steve.popMovies.model.MovieEntry
import com.steve.popMovies.model.MovieListViewModel

class MovieRVAdapter(private val context: Context, private val movieList: ArrayList<MovieEntry>) :
    RecyclerView.Adapter<MovieViewHolder>() {
    private val TAG = "MovieAdapter"
    lateinit var viewModel: MovieListViewModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        val movie = movieList.get(position)
        holder.bind(movie)
        holder.binding.imageView.setOnClickListener{

        }
//        holder.itemView.setOnClickListener {
//            MainActivity().showEntryFragment(position)
//        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}