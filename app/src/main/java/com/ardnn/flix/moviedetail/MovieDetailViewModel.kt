package com.ardnn.flix.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ardnn.flix.core.data.FlixRepository
import com.ardnn.flix.core.data.source.local.entity.relation.MovieWithGenres
import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.CastResponse
import com.ardnn.flix.core.domain.model.Movie
import com.ardnn.flix.core.vo.Resource

class MovieDetailViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    val movieId = MutableLiveData<Int>()

    var movie: LiveData<Resource<Movie>> =
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
            val movie = movieResource.data
            if (movie != null) {
                val newState = !movie.isFavorite
                flixRepository.setIsFavoriteMovie(movie, newState)
            }
        }
    }
}