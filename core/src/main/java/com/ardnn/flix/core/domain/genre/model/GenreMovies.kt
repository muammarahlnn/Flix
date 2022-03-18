package com.ardnn.flix.core.domain.genre.model

import com.ardnn.flix.core.domain.movies.model.Movie

data class GenreMovies(
    val id: Int,
    var name: String = "",
    var movies: List<Movie> = listOf(),
)