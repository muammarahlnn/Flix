package com.ardnn.flix.core.domain.tvshows.model

data class TvShow(
    val id: Int,
    var title: String = "",
    var firstAirDate: String = "",
    var rating: Float = 0f,
    var posterUrl: String = ""
)