package com.ardnn.flix.ui.movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.MovieDetailEntity

class MovieDetailViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    private val _isSynopsisExtended = MutableLiveData(false)
    val isSynopsisExtended: LiveData<Boolean> = _isSynopsisExtended

    private var movieId = 0

    fun getMovieDetail(): LiveData<MovieDetailEntity> =
        flixRepository.getMovieDetail(movieId)

    fun getIsLoadFailure(): LiveData<Boolean> =
        flixRepository.getIsLoadFailure()

    fun getIsLoading(): LiveData<Boolean> =
        flixRepository.getIsLoading()

    fun setMovieId(movieId: Int) {
        this.movieId = movieId
    }

    fun setIsSynopsisExtended(flag: Boolean) {
        _isSynopsisExtended.value = flag
    }
}