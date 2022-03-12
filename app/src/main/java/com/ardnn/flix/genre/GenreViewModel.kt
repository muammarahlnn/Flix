package com.ardnn.flix.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.core.domain.model.Genre
import com.ardnn.flix.core.domain.usecase.FlixUseCase

class GenreViewModel(private val flixUseCase: FlixUseCase) : ViewModel() {

    private var genreId = 0

    fun getGenreWithMovies(): LiveData<Genre> =
        flixUseCase.getGenreWithMovies(genreId)

    fun getGenreWithTvShows(): LiveData<Genre> =
        flixUseCase.getGenreWithTvShows(genreId)

    fun setGenreId(genreId: Int) {
        this.genreId = genreId
    }
}