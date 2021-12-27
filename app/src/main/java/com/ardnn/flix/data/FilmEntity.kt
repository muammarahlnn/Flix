package com.ardnn.flix.data

data class FilmEntity(
    var title: String,
    var overview: String,
    var releaseDate: String,
    var rating: Double,
    var runtime: Int,
    var poster: Int
)