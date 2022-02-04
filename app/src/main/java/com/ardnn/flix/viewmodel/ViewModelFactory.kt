package com.ardnn.flix.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.remote.RemoteDataSource
import com.ardnn.flix.ui.movie_detail.MovieDetailViewModel
import com.ardnn.flix.ui.movies.MoviesViewModel
import com.ardnn.flix.ui.tvshow_detail.TvShowDetailViewModel
import com.ardnn.flix.ui.tvshows.TvShowsViewModel

class ViewModelFactory private constructor(
    private val flixRepository: FlixRepository
) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        private val flixRepository = FlixRepository.getInstance(RemoteDataSource.getInstance())

        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(flixRepository).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
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
            else -> {
                throw Throwable("Unknown ViewModel class: ${modelClass.name}")
            }
        }
    }
}