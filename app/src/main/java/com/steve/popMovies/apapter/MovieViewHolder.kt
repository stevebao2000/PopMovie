package com.steve.popMovies.apapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.steve.popMovies.R
import com.steve.popMovies.databinding.MovieItemBinding
import com.steve.popMovies.model.Constants
import com.steve.popMovies.model.GlideApp
import com.steve.popMovies.model.MovieEntry

class MovieViewHolder(val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: MovieEntry) {
        println(movie)
        binding.title.text = movie.title
        binding.genre.text = "Genre: " + (if(movie.genreids.size > 0) movie.genreids?.get(0).toString() else "0")
        binding.score.text = "Score: " + (if(movie.score != null) movie.score.toString() else "0")
        var year = movie.releaseYear?.toString()?.split("-")?.get(0) ?: ""
        if (year.length == 0)
            year = "n/a"
        binding.year.text = "Year: $year" //"Year: " + (if (movie.releaseYear != null && movie.releaseYear.length != 0) movie.releaseYear.toString().split("-").get(0) else "n/a")
        if (movie.thumbnail != null)
            Log.e("ViewHolder: ", "image path: " + Constants.image_path + movie.thumbnail)
        else
            Log.e("ViewHolder: ", "image path: null")
        if (movie.thumbnail != null)
            GlideApp.with(binding.imageView.context)
                .load(Constants.image_path + movie.thumbnail)
                .placeholder(R.drawable.loading)
                .fitCenter()
                .override(400, 400)
                .error(R.drawable.error)
                .into(binding.imageView)
        else
            GlideApp.with(binding.imageView.context)
                .load(R.drawable.ic_block)
                .fitCenter()
                .override(400, 400)
                .into(binding.imageView)
    }

    companion object {
        fun create(parent: ViewGroup) : MovieViewHolder {
            val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MovieViewHolder(binding)
        }
    }
}