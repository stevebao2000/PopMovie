package com.steve.popMovies.apapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.steve.popMovies.MainActivity
import com.steve.popMovies.R
import com.steve.popMovies.databinding.MovieItemBinding
import com.steve.popMovies.model.Constants
import com.steve.popMovies.model.GlideApp
import com.steve.popMovies.model.MovieEntry
import com.steve.popMovies.model.MovieListViewModel

class MovieAdapter(private val context: Context, private val movieList: ArrayList<MovieEntry>) :
    RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {
    private val TAG = "MovieAdapter"
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
            binding.genre.text = "Genre: " + movie.genreids.get(0).toString()
            binding.score.text = "Score: " + movie.score.toString()
            binding.year.text = "Year: " + movie.releaseYear.toString().split("-").get(0)
            Log.e(TAG, "image path: " + Constants.image_path + movie.thumbnail)
            GlideApp.with(context)
                .load(Constants.image_path + movie.thumbnail)
                .placeholder(R.drawable.loading)
                .fitCenter()
                .override(200, 200)
                .error(R.drawable.error)
                .into(binding.imageView)
        }
    }
    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<MovieEntry>() {
            override fun areItemsTheSame(oldItem: MovieEntry, newItem: MovieEntry): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: MovieEntry, newItem: MovieEntry): Boolean =
                oldItem == newItem
        }
    }
}