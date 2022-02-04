package com.ardnn.flix.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.MovieEntity

class MoviesViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    private var section = 0

    fun getMovies(page: Int): LiveData<List<MovieEntity>> =
        flixRepository.getMovies(page)

    fun getIsLoading(): LiveData<Boolean> =
        flixRepository.getIsLoading()

    fun setSection(section: Int) {
        this.section = section
    }
}