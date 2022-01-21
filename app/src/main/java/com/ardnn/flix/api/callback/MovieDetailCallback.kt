package com.ardnn.flix.api.callback

import com.ardnn.flix.api.response.MovieDetailResponse

interface MovieDetailCallback {
    fun onSuccess(movieDetailResponse: MovieDetailResponse)
    fun onFailure(message: String)
}