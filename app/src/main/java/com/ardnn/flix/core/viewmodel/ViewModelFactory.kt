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
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Provider

@AppScope
class ViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")

        return creator.get() as T
    }

}