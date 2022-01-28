package com.ardnn.flix.ui.film

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.data.source.local.entity.TvShowEntity

class FilmViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    private var section = 0

    fun getMovies(page: Int): LiveData<List<MovieEntity>> =
        flixRepository.getMovies(page)

    fun getTvShows(page: Int): LiveData<List<TvShowEntity>> =
        flixRepository.getTvShows(page)

    fun getIsLoadFailure(): LiveData<Boolean> =
        flixRepository.getIsLoadFailure()

    fun getIsLoading(): LiveData<Boolean> =
        flixRepository.getIsLoading()

    fun setSection(section: Int) {
        this.section = section
    }
}