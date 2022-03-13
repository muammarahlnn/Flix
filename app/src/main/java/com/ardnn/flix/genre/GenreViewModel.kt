package com.ardnn.flix.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.ardnn.flix.core.domain.model.Genre
import com.ardnn.flix.core.domain.usecase.FlixUseCase

class GenreViewModel(private val flixUseCase: FlixUseCase) : ViewModel() {

    private var genreId = 0

    fun getGenreWithMovies(): LiveData<Genre> =
        LiveDataReactiveStreams.fromPublisher(flixUseCase.getGenreWithMovies(genreId))

    fun getGenreWithTvShows(): LiveData<Genre> =
        LiveDataReactiveStreams.fromPublisher(flixUseCase.getGenreWithTvShows(genreId))

    fun setGenreId(genreId: Int) {
        this.genreId = genreId
    }
}