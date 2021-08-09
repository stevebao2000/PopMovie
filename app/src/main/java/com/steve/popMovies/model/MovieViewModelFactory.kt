package com.steve.popMovies.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.steve.popMovies.repository.MovieRepository
import java.lang.IllegalArgumentException

class MovieViewModelFactory(private val repository: MovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom((MovieEntryViewModel::class.java))) {
            @Suppress("UNCHECKED_CAST")
            return MovieEntryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}