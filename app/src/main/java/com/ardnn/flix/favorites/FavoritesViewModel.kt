package com.ardnn.flix.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ardnn.flix.core.data.FlixRepository
import com.ardnn.flix.core.data.source.local.entity.MovieEntity
import com.ardnn.flix.core.data.source.local.entity.TvShowEntity

class FavoritesViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    private var section = 0

    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>> =
        flixRepository.getFavoriteMovies()

    fun getFavoriteTvShows(): LiveData<PagedList<TvShowEntity>> =
        flixRepository.getFavoriteTvShows()

    fun setSection(section: Int) {
        this.section = section
    }

    fun setIsFavoriteMovie(movie: MovieEntity) {
        val newState = !movie.isFavorite
        flixRepository.setIsFavoriteMovie(movie, newState)
    }

    fun setIsFavoriteTvShow(tvShow: TvShowEntity) {
        val newState = !tvShow.isFavorite
        flixRepository.setIsFavoriteTvShow(tvShow, newState)
    }
}