package com.steve.popMovies.model

import com.google.gson.annotations.SerializedName

data class MovieEntry(
    @SerializedName("id")
    var id: Int,
    @SerializedName("poster_path")
    var thumbnail: String,
    @SerializedName("title")
    var title: String = "Error: movie not defined",
    @SerializedName("genre_ids")
    var genreids: List<Int>,
    @SerializedName("popularity")
    var score: Float,
    @SerializedName("release_date")
    var releaseYear: String
)