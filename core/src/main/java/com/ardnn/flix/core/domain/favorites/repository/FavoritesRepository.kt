package com.ardnn.flix.core.domain.favorites.repository

import com.ardnn.flix.core.domain.moviedetail.model.MovieDetail
import com.ardnn.flix.core.domain.tvshowdetail.model.TvShowDetail
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    fun getFavoriteMovies(): Flow<List<MovieDetail>>

    fun getFavoriteTvShows(): Flow<List<TvShowDetail>>

    fun setIsFavoriteMovie(movie: MovieDetail, state: Boolean)

    fun setIsFavoriteTvShow(tvShow: TvShowDetail, state: Boolean)
}