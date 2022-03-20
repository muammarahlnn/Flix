package com.ardnn.flix.moviedetail

import androidx.lifecycle.*
import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.domain.moviedetail.model.Cast
import com.ardnn.flix.core.domain.moviedetail.model.MovieDetail
import com.ardnn.flix.core.domain.moviedetail.usecase.MovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailUseCase: MovieDetailUseCase
) : ViewModel() {

    private val movieId = MutableLiveData<Int>()
    fun setMovieId(movieId: Int) {
        this.movieId.value = movieId
    }

    var movie: LiveData<Resource<MovieDetail>> =
        Transformations.switchMap(movieId) {
            movieDetailUseCase.getMovieDetail(it).asLiveData()
        }

    var movieCasts: LiveData<Resource<List<Cast>>> =
        Transformations.switchMap(movieId) {
            movieDetailUseCase.getMovieCasts(it).asLiveData()
        }

    private val _isSynopsisExtended = MutableLiveData(false)

    val isSynopsisExtended: LiveData<Boolean> = _isSynopsisExtended

    fun setIsSynopsisExtended(flag: Boolean) {
        _isSynopsisExtended.value = flag
    }

    fun setIsFavorite() {
        val movieResource = movie.value
        if (movieResource != null) {
            val movie = movieResource.data
            if (movie != null) {
                val newState = !movie.isFavorite
                movieDetailUseCase.setIsFavoriteMovie(movie, newState)
            }
        }
    }
}