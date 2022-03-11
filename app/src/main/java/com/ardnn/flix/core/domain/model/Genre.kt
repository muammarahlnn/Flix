package com.ardnn.flix.core.domain.model

data class Genre(
    val id: Int,
    var name: String = "",
    var movies: List<Movie> = listOf(),
    var tvShows: List<TvShow> = listOf()
)