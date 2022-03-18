package com.ardnn.flix.core.di

import com.ardnn.flix.core.data.repositoryimpl.*
import com.ardnn.flix.core.domain.favorites.repository.FavoritesRepository
import com.ardnn.flix.core.domain.genre.repository.GenreRepository
import com.ardnn.flix.core.domain.moviedetail.repository.MovieDetailRepository
import com.ardnn.flix.core.domain.movies.repository.MoviesRepository
import com.ardnn.flix.core.domain.tvshowdetail.repository.TvShowDetailRepository
import com.ardnn.flix.core.domain.tvshows.repository.TvShowsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideMoviesRepository(moviesRepository: MoviesRepositoryImpl): MoviesRepository

    @Binds
    abstract fun provideTvShowsRepository(tvShowsRepository: TvShowsRepositoryImpl): TvShowsRepository

    @Binds
    abstract fun provideFavoritesRepository(favoritesRepository: FavoritesRepositoryImpl): FavoritesRepository

    @Binds
    abstract fun provideMovieDetailRepository(movieDetailRepository: MovieDetailRepositoryImpl): MovieDetailRepository

    @Binds
    abstract fun provideTvShowDetailRepository(tvShowDetailRepository: TvShowDetailRepositoryImpl): TvShowDetailRepository

    @Binds
    abstract fun provideGenreRepository(genreRepository: GenreRepositoryImpl): GenreRepository
}