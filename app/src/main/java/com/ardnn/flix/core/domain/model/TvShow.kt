package com.ardnn.flix.core.domain.model

data class TvShow(
    val id: Int,
    var title: String? = null,
    var overview: String? = null,
    var firstAirDate: String? = null,
    var lastAirDate: String? = null,
    var runtime: Int? = null,
    var rating: Float? = null,
    var popularity: Float? = null,
    var posterUrl: String? = null,
    var wallpaperUrl: String? = null,
    var numberOfEpisodes: Int? = null,
    var numberOfSeasons: Int? = null,
    var genres: List<Genre> = listOf(),
    var isFavorite: Boolean = false,
    var isDetailFetched: Boolean = false
)