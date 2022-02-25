package com.ardnn.flix.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.MovieDetailEntity
import com.ardnn.flix.data.source.local.entity.TvShowDetailEntity

class FavoritesViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    private var section = 0

    fun getFavoriteMovies(): LiveData<PagedList<MovieDetailEntity>> =
        flixRepository.getFavoriteMovies()

    fun getFavoriteTvShows(): LiveData<PagedList<TvShowDetailEntity>> =
        flixRepository.getFavoriteTvShows()

    fun setSection(section: Int) {
        this.section = section
    }

    fun setIsFavoriteMovie(movieDetail: MovieDetailEntity) {
        val newState = !movieDetail.isFavorite
        flixRepository.setIsFavoriteMovieDetail(movieDetail, newState)
    }

    fun setIsFavoriteTvShow(tvShowDetail: TvShowDetailEntity) {
        val newState = !tvShowDetail.isFavorite
        flixRepository.setIsFavoriteTvShowDetail(tvShowDetail, newState)
    }
}