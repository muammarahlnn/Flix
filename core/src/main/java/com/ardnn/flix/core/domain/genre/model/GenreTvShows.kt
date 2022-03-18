package com.ardnn.flix.core.domain.genre.model

import com.ardnn.flix.core.domain.tvshows.model.TvShow

data class GenreTvShows(
    val id: Int,
    var name: String = "",
    var tvShows: List<TvShow> = listOf()
)