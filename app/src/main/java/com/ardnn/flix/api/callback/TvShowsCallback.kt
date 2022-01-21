package com.ardnn.flix.api.callback

import com.ardnn.flix.api.response.TvShow

interface TvShowsCallback {
    fun onSuccess(tvShows: List<TvShow>)
    fun onFailure(message: String)
}