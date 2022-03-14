package com.ardnn.flix.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ardnn.flix.core.domain.model.Movie
import com.ardnn.flix.core.domain.model.TvShow
import com.ardnn.flix.core.domain.usecase.FlixUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val flixUseCase: FlixUseCase
) : ViewModel() {

    private var section = 0

    fun getFavoriteMovies(): LiveData<List<Movie>> =
        flixUseCase.getFavoriteMovies().asLiveData()

    fun getFavoriteTvShows(): LiveData<List<TvShow>> =
        flixUseCase.getFavoriteTvShows().asLiveData()

    fun setSection(section: Int) {
        this.section = section
    }

    fun setIsFavoriteMovie(movie: Movie) {
        val newState = !movie.isFavorite
        flixUseCase.setIsFavoriteMovie(movie, newState)
    }

    fun setIsFavoriteTvShow(tvShow: TvShow) {
        val newState = !tvShow.isFavorite
        flixUseCase.setIsFavoriteTvShow(tvShow, newState)
    }
}