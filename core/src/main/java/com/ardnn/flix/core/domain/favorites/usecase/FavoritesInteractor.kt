package com.ardnn.flix.core.domain.favorites.usecase

import com.ardnn.flix.core.domain.favorites.repository.FavoritesRepository
import com.ardnn.flix.core.domain.moviedetail.model.MovieDetail
import com.ardnn.flix.core.domain.tvshowdetail.model.TvShowDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesInteractor @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : FavoritesUseCase {

    override fun getFavoriteMovies(): Flow<List<MovieDetail>> =
        favoritesRepository.getFavoriteMovies()

    override fun getFavoriteTvShows(): Flow<List<TvShowDetail>> =
        favoritesRepository.getFavoriteTvShows()

    override fun setIsFavoriteMovie(movie: MovieDetail, state: Boolean) =
        favoritesRepository.setIsFavoriteMovie(movie, state)

    override fun setIsFavoriteTvShow(tvShow: TvShowDetail, state: Boolean) =
        favoritesRepository.setIsFavoriteTvShow(tvShow, state)
}