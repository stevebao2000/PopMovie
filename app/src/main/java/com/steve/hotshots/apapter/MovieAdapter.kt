package com.steve.hotshots.apapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.steve.hotshots.MainActivity
import com.steve.hotshots.R
import com.steve.hotshots.databinding.MovieItemBinding
import com.steve.hotshots.model.MovieEntry
import com.steve.hotshots.model.MovieListViewModel
import com.steve.hotshots.ui.ListFragment

class MovieAdapter(private val context: Context, private val movieList: ArrayList<MovieEntry>) :
    RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    lateinit var viewModel: MovieListViewModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val movie = movieList.get(position)
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            MainActivity().showEntryFragment(position)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MyViewHolder(val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieEntry) {
            binding.title.text = movie.title
            binding.genre.text = movie.genreName
            binding.score.text = movie.score.toString()
            binding.year.text = movie.releaseYear.toString()
            Glide.with(itemView.context)
                .load(movie.thumbnail)
                .placeholder(R.drawable.loading)
                .fitCenter()
                .into(binding.imageView)
        }
    }
}