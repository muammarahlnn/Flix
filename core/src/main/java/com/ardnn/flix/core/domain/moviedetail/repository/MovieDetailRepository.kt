package com.ardnn.flix.core.domain.moviedetail.repository

import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.domain.moviedetail.model.Cast
import com.ardnn.flix.core.domain.moviedetail.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {

    fun getMovieDetail(movieId: Int): Flow<Resource<MovieDetail>>

    fun getMovieCasts(movieId: Int): Flow<Resource<List<Cast>>>

    fun setIsFavoriteMovie(movieDetail: MovieDetail, state: Boolean)
}