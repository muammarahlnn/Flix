package com.ardnn.flix.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ardnn.flix.core.domain.model.Genre
import com.ardnn.flix.core.domain.usecase.FlixUseCase

class GenreViewModel(private val flixUseCase: FlixUseCase) : ViewModel() {

    private var genreId = 0

    fun getGenreWithMovies(): LiveData<Genre> =
        flixUseCase.getGenreWithMovies(genreId).asLiveData()

    fun getGenreWithTvShows(): LiveData<Genre> =
        flixUseCase.getGenreWithTvShows(genreId).asLiveData()

    fun setGenreId(genreId: Int) {
        this.genreId = genreId
    }
}