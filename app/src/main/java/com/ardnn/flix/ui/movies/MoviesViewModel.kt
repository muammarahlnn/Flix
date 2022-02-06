package com.ardnn.flix.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.vo.Resource

class MoviesViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    private var section = 0

    fun getNowPlayingMovies(page: Int): LiveData<Resource<List<MovieEntity>>> =
        flixRepository.getNowPlayingMovies(page)

    fun getTopRatedMovies(page: Int): LiveData<Resource<List<MovieEntity>>> =
        flixRepository.getTopRatedMovies(page)

    fun setSection(section: Int) {
        this.section = section
    }
}