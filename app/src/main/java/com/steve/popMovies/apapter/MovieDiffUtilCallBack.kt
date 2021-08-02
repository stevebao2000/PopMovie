package com.steve.popMovies.apapter

import androidx.recyclerview.widget.DiffUtil
import com.steve.popMovies.model.MovieEntry

class MovieDiffUtilCallBack : DiffUtil.ItemCallback<MovieEntry>() {
    override fun areItemsTheSame(oldItem: MovieEntry, newItem: MovieEntry): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: MovieEntry, newItem: MovieEntry): Boolean {
        return oldItem == newItem
    }
}