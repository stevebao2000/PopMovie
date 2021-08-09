package com.steve.popMovies.model

import android.util.Log
import androidx.lifecycle.*
import com.steve.popMovies.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieEntryViewModel(private val repository: MovieRepository) : ViewModel() {
    private val TAG = "MovieEntryViewModel"
    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val queryLiveData = MutableLiveData<String>()
    val movieResult: LiveData<MovieSearchResult> = queryLiveData.switchMap { queryString ->
        liveData {
            val movies = repository.getSearchResultStream(queryString).asLiveData(Dispatchers.Main)
            emitSource(movies)
        }
    }

    fun searchMovie(query: String) {
        queryLiveData.postValue(query)
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        Log.e(TAG, "visibleItemCount: $visibleItemCount, lastVisibleItemPosition: $lastVisibleItemPosition, totalItemCount: $totalItemCount")
        if(visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            Log.e(TAG, "Need more items........................")
            val immutableQuery = queryLiveData.value
            if (immutableQuery != null) {
                viewModelScope.launch {
                    repository.requestMore(immutableQuery)
                }
            }
        }
    }
}