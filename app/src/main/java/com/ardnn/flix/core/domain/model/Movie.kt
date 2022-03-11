package com.ardnn.flix.core.domain.model

data class Movie(
    val id: Int,
    var title: String? = null,
    var overview: String? = null,
    var releaseDate: String? = null,
    var runtime: Int? = null,
    var rating: Float? = null,
    var popularity: Float? = null,
    var posterUrl: String? = null,
    var wallpaperUrl: String? = null,
    var genres: List<Genre> = listOf(),
    var isFavorite: Boolean = false,
    var isDetailFetched: Boolean = false
)