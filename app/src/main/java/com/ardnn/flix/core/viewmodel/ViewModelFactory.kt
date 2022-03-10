package com.ardnn.flix.core.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ardnn.flix.core.data.FlixRepository
import com.ardnn.flix.core.di.Injection
import com.ardnn.flix.favorites.FavoritesViewModel
import com.ardnn.flix.genre.GenreViewModel
import com.ardnn.flix.moviedetail.MovieDetailViewModel
import com.ardnn.flix.movies.MoviesViewModel
import com.ardnn.flix.tvshowdetail.TvShowDetailViewModel
import com.ardnn.flix.tvshows.TvShowsViewModel

class ViewModelFactory private constructor(
    private val flixRepository: FlixRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MoviesViewModel::class.java) -> {
                MoviesViewModel(flixRepository) as T
            }
            modelClass.isAssignableFrom(TvShowsViewModel::class.java) -> {
                TvShowsViewModel(flixRepository) as T
            }
            modelClass.isAssignableFrom(MovieDetailViewModel::class.java) -> {
                MovieDetailViewModel(flixRepository) as T
            }
            modelClass.isAssignableFrom(TvShowDetailViewModel::class.java) -> {
                TvShowDetailViewModel(flixRepository) as T
            }
            modelClass.isAssignableFrom(FavoritesViewModel::class.java) -> {
                FavoritesViewModel(flixRepository) as T
            }
            modelClass.isAssignableFrom(GenreViewModel::class.java) -> {
                GenreViewModel(flixRepository) as T
            }
            else -> {
                throw Throwable("Unknown ViewModel class: ${modelClass.name}")
            }
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }
}