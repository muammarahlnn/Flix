package com.ardnn.flix.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.core.data.FlixRepository
import com.ardnn.flix.core.data.source.local.entity.relation.GenreWithMovies
import com.ardnn.flix.core.data.source.local.entity.relation.GenreWithTvShows

class GenreViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    private var genreId = 0

    fun getGenreWithMovies(): LiveData<GenreWithMovies> =
        flixRepository.getGenreWithMovies(genreId)

    fun getGenreWithTvShows(): LiveData<GenreWithTvShows> =
        flixRepository.getGenreWithTvShows(genreId)

    fun setGenreId(genreId: Int) {
        this.genreId = genreId
    }
}