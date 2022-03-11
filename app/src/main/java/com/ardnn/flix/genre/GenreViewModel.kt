package com.ardnn.flix.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.core.data.FlixRepository
import com.ardnn.flix.core.domain.model.Genre

class GenreViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    private var genreId = 0

    fun getGenreWithMovies(): LiveData<Genre> =
        flixRepository.getGenreWithMovies(genreId)

    fun getGenreWithTvShows(): LiveData<Genre> =
        flixRepository.getGenreWithTvShows(genreId)

    fun setGenreId(genreId: Int) {
        this.genreId = genreId
    }
}