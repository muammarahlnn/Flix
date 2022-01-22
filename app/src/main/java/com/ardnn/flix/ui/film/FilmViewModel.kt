package com.ardnn.flix.ui.film

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.api.callback.MoviesCallback
import com.ardnn.flix.api.callback.TvShowsCallback
import com.ardnn.flix.api.config.MovieApiConfig
import com.ardnn.flix.api.config.TvShowApiConfig
import com.ardnn.flix.api.response.Movie
import com.ardnn.flix.api.response.TvShow

class FilmViewModel(section: Int) : ViewModel() {

    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>> = _movieList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _tvShowList = MutableLiveData<List<TvShow>>()
    val tvShowList: LiveData<List<TvShow>> = _tvShowList

    companion object {
        private const val TAG = "FilmViewModel"
    }

    init {
        when (section) {
            0 -> { // movies
                fetchMovies()
            }
            1 -> { // tv shows
                fetchTvShows()
            }
        }
    }

    private fun fetchMovies() {
        // show progressbar
        _isLoading.value = true

        MovieApiConfig.getNowPlayingMovies(1, object : MoviesCallback {
            override fun onSuccess(movies: List<Movie>) {
                // hide progressbar
                _isLoading.value = false

                _movieList.value = movies
            }

            override fun onFailure(message: String) {
                // hide progressbar
                _isLoading.value = false

                Log.d(TAG, message)
            }
        })
    }

    private fun fetchTvShows() {
        // show progressbar
        _isLoading.value = true

        TvShowApiConfig.getAiringTodayTvShows(1, object : TvShowsCallback {
            override fun onSuccess(tvShows: List<TvShow>) {
                // hide progressbar
                _isLoading.value = false

                _tvShowList.value = tvShows
            }

            override fun onFailure(message: String) {
                // hide progressbar
                _isLoading.value = false

                Log.d(TAG, message)
            }
        })
    }
}