package com.ardnn.flix.data

import androidx.lifecycle.LiveData
import com.ardnn.flix.data.source.local.entity.MovieDetailEntity
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.data.source.local.entity.TvShowDetailEntity
import com.ardnn.flix.data.source.local.entity.TvShowEntity

interface FlixDataSource {
    fun getMovies(page: Int): LiveData<List<MovieEntity>>
    fun getMovieDetail(movieId: Int): LiveData<MovieDetailEntity>
    fun getTvShows(page: Int): LiveData<List<TvShowEntity>>
    fun getTvShowDetail(tvShowId: Int): LiveData<TvShowDetailEntity>
}