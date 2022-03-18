package com.ardnn.flix.di

import com.ardnn.flix.core.domain.favorites.usecase.FavoritesInteractor
import com.ardnn.flix.core.domain.favorites.usecase.FavoritesUseCase
import com.ardnn.flix.core.domain.genre.usecase.GenreInteractor
import com.ardnn.flix.core.domain.genre.usecase.GenreUseCase
import com.ardnn.flix.core.domain.moviedetail.usecase.MovieDetailInteractor
import com.ardnn.flix.core.domain.moviedetail.usecase.MovieDetailUseCase
import com.ardnn.flix.core.domain.movies.usecase.MoviesInteractor
import com.ardnn.flix.core.domain.movies.usecase.MoviesUseCase
import com.ardnn.flix.core.domain.tvshowdetail.usecase.TvShowDetailInteractor
import com.ardnn.flix.core.domain.tvshowdetail.usecase.TvShowDetailUseCase
import com.ardnn.flix.core.domain.tvshows.usecase.TvShowsInteractor
import com.ardnn.flix.core.domain.tvshows.usecase.TvShowsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideMoviesUseCase(moviesInteractor: MoviesInteractor): MoviesUseCase

    @Binds
    @Singleton
    abstract fun provideTvShowsUseCase(tvShowsInteractor: TvShowsInteractor): TvShowsUseCase

    @Binds
    @Singleton
    abstract fun provideFavoritesUseCase(favoritesInteractor: FavoritesInteractor): FavoritesUseCase

    @Binds
    @Singleton
    abstract fun provideMovieDetailUseCase(movieDetailInteractor: MovieDetailInteractor): MovieDetailUseCase

    @Binds
    @Singleton
    abstract fun provideTvShowDetailUseCase(tvShowDetailInteractor: TvShowDetailInteractor): TvShowDetailUseCase

    @Binds
    @Singleton
    abstract fun provideGenreUseCase(genreInteractor: GenreInteractor): GenreUseCase
}