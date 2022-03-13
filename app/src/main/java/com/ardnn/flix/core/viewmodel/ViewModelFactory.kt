package com.ardnn.flix.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ardnn.flix.core.domain.usecase.FlixUseCase
import com.ardnn.flix.di.AppScope
import com.ardnn.flix.favorites.FavoritesViewModel
import com.ardnn.flix.genre.GenreViewModel
import com.ardnn.flix.moviedetail.MovieDetailViewModel
import com.ardnn.flix.movies.MoviesViewModel
import com.ardnn.flix.tvshowdetail.TvShowDetailViewModel
import com.ardnn.flix.tvshows.TvShowsViewModel
import javax.inject.Inject

@AppScope
class ViewModelFactory @Inject constructor(
    private val flixUseCase: FlixUseCase
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MoviesViewModel::class.java) -> {
                MoviesViewModel(flixUseCase) as T
            }
            modelClass.isAssignableFrom(TvShowsViewModel::class.java) -> {
                TvShowsViewModel(flixUseCase) as T
            }
            modelClass.isAssignableFrom(MovieDetailViewModel::class.java) -> {
                MovieDetailViewModel(flixUseCase) as T
            }
            modelClass.isAssignableFrom(TvShowDetailViewModel::class.java) -> {
                TvShowDetailViewModel(flixUseCase) as T
            }
            modelClass.isAssignableFrom(FavoritesViewModel::class.java) -> {
                FavoritesViewModel(flixUseCase) as T
            }
            modelClass.isAssignableFrom(GenreViewModel::class.java) -> {
                GenreViewModel(flixUseCase) as T
            }
            else -> {
                throw Throwable("Unknown ViewModel class: ${modelClass.name}")
            }
        }
    }

}