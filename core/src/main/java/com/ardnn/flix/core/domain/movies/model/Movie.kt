package com.ardnn.flix.core.domain.movies.model

data class Movie(
    val id: Int,
    var title: String = "",
    var releaseDate: String = "",
    var rating: Float = 0f,
    var posterUrl: String = ""
)