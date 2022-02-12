package com.ardnn.flix.ui.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.relation.GenreWithMovieDetails
import com.ardnn.flix.data.source.local.entity.relation.GenreWithTvShowDetails

class GenreViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    private var genreId = 0

    fun getGenreWithMovies(): LiveData<GenreWithMovieDetails> =
        flixRepository.getGenreWithMovies(genreId)

    fun getGenreWithTvShows(): LiveData<GenreWithTvShowDetails> =
        flixRepository.getGenreWithTvShows(genreId)

    fun setGenreId(genreId: Int) {
        this.genreId = genreId
    }
}