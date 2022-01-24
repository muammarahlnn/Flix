package com.ardnn.flix.data.source.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilmEntity(
    var title: String?,
    var overview: String?,
    var releaseDate: String?,
    var rating: Double?,
    var runtime: Int?,
    var poster: Int?
) : Parcelable