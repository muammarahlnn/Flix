package com.ardnn.flix.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ardnn.flix.core.domain.favorites.usecase.FavoritesUseCase
import com.ardnn.flix.core.domain.moviedetail.model.MovieDetail
import com.ardnn.flix.core.domain.tvshowdetail.model.TvShowDetail

class FavoritesViewModel(private val favoritesUseCase: FavoritesUseCase) : ViewModel() {

    private var section = 0

    fun getFavoriteMovies(): LiveData<List<MovieDetail>> =
        favoritesUseCase.getFavoriteMovies().asLiveData()

    fun getFavoriteTvShows(): LiveData<List<TvShowDetail>> =
        favoritesUseCase.getFavoriteTvShows().asLiveData()

    fun setSection(section: Int) {
        this.section = section
    }

    fun setIsFavoriteMovie(movie: MovieDetail) {
        val newState = !movie.isFavorite
        favoritesUseCase.setIsFavoriteMovie(movie, newState)
    }

    fun setIsFavoriteTvShow(tvShow: TvShowDetail) {
        val newState = !tvShow.isFavorite
        favoritesUseCase.setIsFavoriteTvShow(tvShow, newState)
    }
}