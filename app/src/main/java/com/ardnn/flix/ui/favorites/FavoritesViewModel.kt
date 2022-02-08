package com.ardnn.flix.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.MovieDetailEntity
import com.ardnn.flix.data.source.local.entity.TvShowDetailEntity
import com.ardnn.flix.utils.SortUtils

class FavoritesViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    private var section = 0

    private val _moviesSort = MutableLiveData(SortUtils.DEFAULT)
    val moviesSort: LiveData<String> = _moviesSort

    private val _tvShowsSort = MutableLiveData<String>()
    val tvShowsSort: LiveData<String> = _tvShowsSort

    fun getFavoriteMovies(filter: String): LiveData<PagedList<MovieDetailEntity>> =
        flixRepository.getFavoriteMovies(filter)

    fun getFavoriteTvShows(filter: String): LiveData<PagedList<TvShowDetailEntity>> =
        flixRepository.getFavoriteTvShows(filter)

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

    fun setMoviesSort(filter: String) {
        _moviesSort.value = filter
    }

    fun setTvShowsSort(filter: String) {
        _tvShowsSort.value = filter
    }
}