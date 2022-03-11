package com.ardnn.flix.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.core.data.FlixRepository
import com.ardnn.flix.core.domain.model.Movie
import com.ardnn.flix.core.domain.model.TvShow

class FavoritesViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    private var section = 0

    fun getFavoriteMovies(): LiveData<List<Movie>> =
        flixRepository.getFavoriteMovies()

    fun getFavoriteTvShows(): LiveData<List<TvShow>> =
        flixRepository.getFavoriteTvShows()

    fun setSection(section: Int) {
        this.section = section
    }

    fun setIsFavoriteMovie(movie: Movie) {
        val newState = !movie.isFavorite
        flixRepository.setIsFavoriteMovie(movie, newState)
    }

    fun setIsFavoriteTvShow(tvShow: TvShow) {
        val newState = !tvShow.isFavorite
        flixRepository.setIsFavoriteTvShow(tvShow, newState)
    }
}