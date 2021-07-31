package com.steve.popMovies.model

import com.google.gson.annotations.SerializedName

data class TMDb(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieEntry>,
    @SerializedName("total_pages")
    val total_pages: Int,
    @SerializedName("total_results")
    val total_results: Int
)