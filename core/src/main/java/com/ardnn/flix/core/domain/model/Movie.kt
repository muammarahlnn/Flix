package com.ardnn.flix.core.domain.model

data class Movie(
    val id: Int,
    var title: String = "",
    var overview: String = "",
    var releaseDate: String = "",
    var runtime: Int = 0,
    var rating: Float = 0f,
    var popularity: Float = 0f,
    var posterUrl: String = "",
    var wallpaperUrl: String = "",
    var genres: List<Genre> = listOf(),
    var isFavorite: Boolean = false,
    var isDetailFetched: Boolean = false
)