package com.ardnn.flix.ui.movie_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.api.callback.MovieDetailCallback
import com.ardnn.flix.api.config.MovieApiConfig
import com.ardnn.flix.api.response.MovieDetailResponse

class MovieDetailViewModel(movieId: Int) : ViewModel() {

    private val _movieDetail = MutableLiveData<MovieDetailResponse>()
    val movieDetail: LiveData<MovieDetailResponse> = _movieDetail

    private val _isSynopsisExtended = MutableLiveData(false)
    val isSynopsisExtended: LiveData<Boolean> = _isSynopsisExtended

    companion object {
        private const val TAG = "MovieDetailViewModel"
    }

    init {
        fetchMovieDetail(movieId)
    }

    private fun fetchMovieDetail(movieId: Int) {
        MovieApiConfig.getMovieDetail(movieId, object : MovieDetailCallback {
            override fun onSuccess(movieDetailResponse: MovieDetailResponse) {
                _movieDetail.value = movieDetailResponse
            }

            override fun onFailure(message: String) {
                Log.d(TAG, message)
            }
        })
    }

    fun setIsSynopsisExtended(flag: Boolean) {
        _isSynopsisExtended.value = flag
    }
}