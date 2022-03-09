package com.ardnn.flix.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.relation.MovieWithGenres
import com.ardnn.flix.data.source.remote.ApiResponse
import com.ardnn.flix.data.source.remote.response.CastResponse
import com.ardnn.flix.vo.Resource

class MovieDetailViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    val movieId = MutableLiveData<Int>()

    var movie: LiveData<Resource<MovieWithGenres>> =
        Transformations.switchMap(movieId) {
            flixRepository.getMovieWithGenres(it)
        }

    var movieCredits: LiveData<ApiResponse<List<CastResponse>>> =
        Transformations.switchMap(movieId) {
            flixRepository.getMovieCredits(it)
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
        val movieResource = movie.value
        if (movieResource != null) {
            val movieEntity = movieResource.data
            if (movieEntity != null) {
                val newState = !movieEntity.movie.isFavorite
                flixRepository.setIsFavoriteMovie(movieEntity.movie, newState)
            }
        }
    }
}