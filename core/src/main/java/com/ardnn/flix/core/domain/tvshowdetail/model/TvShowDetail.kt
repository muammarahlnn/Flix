package com.ardnn.flix.core.domain.tvshowdetail.model

import android.os.Parcelable
import com.ardnn.flix.core.domain.genre.model.Genre
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShowDetail (
    val id: Int,
    var title: String = "",
    var overview: String = "",
    var firstAirDate: String = "",
    var lastAirDate: String = "",
    var runtime: Int = 0,
    var rating: Float = 0f,
    var popularity: Float = 0f,
    var posterUrl: String = "",
    var wallpaperUrl: String = "",
    var numberOfEpisodes: Int = 0,
    var numberOfSeasons: Int = 0,
    var genres: List<Genre> = listOf(),
    var isFavorite: Boolean = false,
    var isDetailFetched: Boolean = false
) : Parcelable