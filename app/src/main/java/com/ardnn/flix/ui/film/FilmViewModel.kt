package com.ardnn.flix.ui.film

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.data.source.local.entity.TvShowEntity

class FilmViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFailure = MutableLiveData<Boolean>()
    val isFailure: LiveData<Boolean> = _isFailure

    private var section = 0

    fun setSection(section: Int) {
        this.section = section
    }

    fun getMovies(page: Int): LiveData<List<MovieEntity>> =
        flixRepository.getMovies(page)

    fun getTvShows(page: Int): LiveData<List<TvShowEntity>> =
        flixRepository.getTvShows(page)
}