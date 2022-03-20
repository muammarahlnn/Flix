package com.ardnn.flix.core.domain.moviedetail.usecase

import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.domain.moviedetail.model.Cast
import com.ardnn.flix.core.domain.moviedetail.model.MovieDetail
import com.ardnn.flix.core.domain.moviedetail.repository.MovieDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailInteractor @Inject constructor(
    private val movieDetailRepository: MovieDetailRepository
) : MovieDetailUseCase {

    override fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>> =
        movieDetailRepository.getMovieDetail(movieId)

    override fun getMovieCasts(movieId: Int): Flow<Resource<List<Cast>>> =
        movieDetailRepository.getMovieCasts(movieId)

    override fun setIsFavoriteMovie(movieDetail: MovieDetail, state: Boolean) {
        movieDetailRepository.setIsFavoriteMovie(movieDetail, state)
    }
}