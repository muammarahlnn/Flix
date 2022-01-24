package com.ardnn.flix.utils

import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.data.source.local.entity.TvShowEntity

interface FilmClickListener {
    fun onMovieClicked(movie: MovieEntity)
    fun onTvShowClicked(tvShow: TvShowEntity)
}