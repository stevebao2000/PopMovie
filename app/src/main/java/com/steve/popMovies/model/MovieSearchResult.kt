package com.steve.popMovies.model

import java.lang.Exception

sealed class MovieSearchResult {
    data class Success(val data: List<MovieEntry>) : MovieSearchResult()
    data class Error(val error: Exception): MovieSearchResult()
}
