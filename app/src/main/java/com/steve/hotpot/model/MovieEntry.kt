package com.steve.hotpot.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieEntry(
    var id: Int = 0,
    var thumbnail: String = "",
    var title: String = "Error: movie not defined",
    var genreName: String = "n/a",
    var score: Int = 0,
    var releaseYear: Int = 0
): Parcelable