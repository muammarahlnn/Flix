package com.ardnn.flix.data

import androidx.lifecycle.LiveData
import com.ardnn.flix.data.source.local.entity.MovieDetailEntity
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.data.source.local.entity.TvShowDetailEntity
import com.ardnn.flix.data.source.local.entity.TvShowEntity
import com.ardnn.flix.vo.Resource

interface FlixDataSource {
    fun getNowPlayingMovies(page: Int): LiveData<Resource<List<MovieEntity>>>
    fun getTopRatedMovies(page: Int): LiveData<Resource<List<MovieEntity>>>
    fun getMovieDetail(movieId: Int): LiveData<Resource<MovieDetailEntity>>
    fun getOnTheAirTvShows(page: Int): LiveData<Resource<List<TvShowEntity>>>
    fun getTopRatedTvShows(page: Int): LiveData<Resource<List<TvShowEntity>>>
    fun getTvShowDetail(tvShowId: Int): LiveData<Resource<TvShowDetailEntity>>
    fun setIsFavoriteMovieDetail(movieDetail: MovieDetailEntity, state: Boolean)
    fun setIsFavoriteTvShowDetail(tvShowDetail: TvShowDetailEntity, state: Boolean)
}