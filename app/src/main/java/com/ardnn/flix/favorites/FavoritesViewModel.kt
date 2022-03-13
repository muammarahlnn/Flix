package com.ardnn.flix.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.ardnn.flix.core.domain.model.Movie
import com.ardnn.flix.core.domain.model.TvShow
import com.ardnn.flix.core.domain.usecase.FlixUseCase

class FavoritesViewModel(private val flixUseCase: FlixUseCase) : ViewModel() {

    private var section = 0

    fun getFavoriteMovies(): LiveData<List<Movie>> =
        LiveDataReactiveStreams.fromPublisher(flixUseCase.getFavoriteMovies())

    fun getFavoriteTvShows(): LiveData<List<TvShow>> =
        LiveDataReactiveStreams.fromPublisher(flixUseCase.getFavoriteTvShows())

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