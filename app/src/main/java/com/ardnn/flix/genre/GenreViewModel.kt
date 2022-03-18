package com.ardnn.flix.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ardnn.flix.core.domain.genre.model.GenreMovies
import com.ardnn.flix.core.domain.genre.model.GenreTvShows
import com.ardnn.flix.core.domain.genre.usecase.GenreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val genreUseCase: GenreUseCase
) : ViewModel() {

    private var genreId = 0

    fun getGenreWithMovies(): LiveData<GenreMovies> =
        genreUseCase.getGenreWithMovies(genreId).asLiveData()

    fun getGenreWithTvShows(): LiveData<GenreTvShows> =
        genreUseCase.getGenreWithTvShows(genreId).asLiveData()

    fun setGenreId(genreId: Int) {
        this.genreId = genreId
    }
}