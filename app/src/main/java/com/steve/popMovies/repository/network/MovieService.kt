package com.steve.popMovies.repository.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.steve.popMovies.model.Constants
import com.steve.popMovies.model.MovieEntry
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val API_KEY = "8a0db21487772a3656f33f4386cf27b4"


interface MovieService {
    @GET("/discover/movie")
    suspend fun getPopularMovies(
            @Query("sort_by") sortBy: String,
            @Query("page") pageNum: Int
    ): ArrayList<MovieEntry>

    companion object {
        operator fun invoke(): MovieService {
            // add api_key for every request.
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://www.github.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieService::class.java)
        }
    }
}