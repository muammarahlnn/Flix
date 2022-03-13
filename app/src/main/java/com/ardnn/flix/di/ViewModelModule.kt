package com.ardnn.flix.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ardnn.flix.core.viewmodel.ViewModelFactory
import com.ardnn.flix.favorites.FavoritesViewModel
import com.ardnn.flix.genre.GenreViewModel
import com.ardnn.flix.moviedetail.MovieDetailViewModel
import com.ardnn.flix.movies.MoviesViewModel
import com.ardnn.flix.tvshowdetail.TvShowDetailViewModel
import com.ardnn.flix.tvshows.TvShowsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    abstract fun bindMoviesViewModel(viewModel: MoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TvShowsViewModel::class)
    abstract fun bindTvShowsViewModel(viewModel: TvShowsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    abstract fun bindFavoritesViewModel(viewModel: FavoritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel::class)
    abstract fun bindMovieDetailViewModel(viewModel: MovieDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TvShowDetailViewModel::class)
    abstract fun bindTvShowDetailViewModel(viewModel: TvShowDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GenreViewModel::class)
    abstract fun bindGenreViewModel(viewModel: GenreViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}