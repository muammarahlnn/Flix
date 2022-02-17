package com.ardnn.flix.ui.movie_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.relation.MovieDetailWithGenres
import com.ardnn.flix.data.source.remote.ApiResponse
import com.ardnn.flix.data.source.remote.response.CastResponse
import com.ardnn.flix.vo.Resource

class MovieDetailViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    val movieId = MutableLiveData<Int>()

    var movieDetail: LiveData<Resource<MovieDetailWithGenres>> =
        Transformations.switchMap(movieId) { mMovieId ->
            flixRepository.getMovieDetailWithGenres(mMovieId)
        }

    var movieCredits: LiveData<ApiResponse<List<CastResponse>>> =
        Transformations.switchMap(movieId) { mMovieId ->
            flixRepository.getMovieCredits(mMovieId)
        }

    private val _isSynopsisExtended = MutableLiveData(false)
    val isSynopsisExtended: LiveData<Boolean> = _isSynopsisExtended

    fun setMovieId(movieId: Int) {
        this.movieId.value = movieId
    }

    fun setIsSynopsisExtended(flag: Boolean) {
        _isSynopsisExtended.value = flag
    }

    fun setIsFavorite() {
        val movieDetailResource = movieDetail.value
        if (movieDetailResource != null) {
            val movieDetailEntity = movieDetailResource.data
            if (movieDetailEntity != null) {
                val newState = !movieDetailEntity.movieDetail.isFavorite
                flixRepository.setIsFavoriteMovieDetail(movieDetailEntity.movieDetail, newState)
            }
        }
    }
}