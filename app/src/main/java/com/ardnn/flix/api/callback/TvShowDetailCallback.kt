package com.ardnn.flix.api.callback

import com.ardnn.flix.api.response.TvShowDetailResponse

interface TvShowDetailCallback {
    fun onSuccess(tvShowDetailResponse: TvShowDetailResponse)
    fun onFailure(message: String)
}