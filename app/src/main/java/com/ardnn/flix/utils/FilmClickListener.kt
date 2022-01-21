package com.ardnn.flix.utils

import com.ardnn.flix.api.response.Movie
import com.ardnn.flix.api.response.TvShow

interface FilmClickListener {
    fun onMovieClicked(movie: Movie)
    fun onTvShowClicked(tvShow: TvShow)
}