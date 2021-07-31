package com.steve.popMovies.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MovieListViewModel: ViewModel() {
    var movies = MutableLiveData<MutableList<MovieEntry>>()
    var list = ArrayList<MovieEntry>()

    fun addMovies(movieList: List<MovieEntry>) {
        list.addAll(movieList)
        movies.postValue(list)
    }

    fun getMovieAt(index: Int) : MovieEntry {
        if (list.size > index)
            return list[index]
        else
            return list.last() // return last one
    }
}