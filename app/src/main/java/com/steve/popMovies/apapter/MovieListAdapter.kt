package com.steve.popMovies.apapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.steve.popMovies.model.MovieEntry
import com.steve.popMovies.model.MovieListViewModel

class MovieListAdapter : ListAdapter<MovieEntry, MovieViewHolder>(MOVIE_COMPARATOR){


    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<MovieEntry>() {
            override fun areItemsTheSame(oldItem: MovieEntry, newItem: MovieEntry): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: MovieEntry, newItem: MovieEntry): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let {
            holder.bind(movie)
        }
    }
}