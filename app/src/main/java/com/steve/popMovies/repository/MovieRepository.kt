package com.steve.popMovies.repository

import android.util.Log
import com.steve.popMovies.model.MovieEntry
import com.steve.popMovies.model.MovieSearchResult
import com.steve.popMovies.repository.network.MovieService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE = 1
class MovieRepository(private val service: MovieService) {
    private val TAG = "MovieRepository"

    private val inMemoryCache = mutableListOf<MovieEntry>()

    private val searchResults = MutableSharedFlow<MovieSearchResult>(replay = 1)

    private var nextRequestPage = STARTING_PAGE

    private var isRequestInProgress = false

    suspend fun getSearchResultStream(query: String): Flow<MovieSearchResult> {
        inMemoryCache.clear()
        requestAndSaveData(query)
        nextRequestPage++
        return searchResults
    }

    suspend fun requestMore(query: String) {
        if (isRequestInProgress) {
            Log.e(TAG, "isRequestInProgress: $isRequestInProgress, return")
            return
        }
        Log.e(TAG, "requestMore:query: $query, nextRequestPage: $nextRequestPage " )
        val successful = requestAndSaveData(query)
        Log.e(TAG, "requestMore: successful = $successful" )
        if (successful) {
            nextRequestPage++
        }
    }

    private suspend fun requestAndSaveData(query: String) : Boolean {
        isRequestInProgress = true
        var successful = false
        Log.e(TAG, "requestAndSaveData: nextRequestPage = $nextRequestPage" )
        try {
            val response = service.getPopularMovies(query, nextRequestPage)
            val movies = response.results ?: emptyList()
            Log.e(TAG, "Response: Page: ${response.page}, Item/Page = ${response.results.size}, Total Pages = ${response.total_pages}, Total Amount = ${response.total_results}")
            Log.e(TAG, "requestAndSaveData: new movies.size = ${movies.size}" )
            inMemoryCache.addAll(movies)
            Log.e(TAG, "requestAndSaveData: inMameoryCache.size = ${inMemoryCache.size}" )
//            searchResults.emit(MovieSearchResult.Success(movies))
            searchResults.emit(MovieSearchResult.Success(inMemoryCache))
            successful = true
        } catch (exception: IOException) {
            searchResults.emit(MovieSearchResult.Error(exception))
        } catch (exception: HttpException) {
            searchResults.emit(MovieSearchResult.Error(exception))
        }
        isRequestInProgress = false
        return successful
    }

}