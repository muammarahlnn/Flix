package com.ardnn.flix.api.callback

import com.ardnn.flix.api.response.Movie

interface MoviesCallback {
    fun onSuccess(movies: List<Movie>)
    fun onFailure(message: String)
}