package com.ardnn.flix.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.domain.movies.model.Movie
import com.ardnn.flix.core.domain.movies.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    private var section = 0

    private val sortArr = arrayOf("", "", "", "")

    private val _moviesSort = MutableLiveData(sortArr)

    val moviesSort: LiveData<Array<String>> = _moviesSort

    fun getMovies(page: Int, filter: String): LiveData<Resource<List<Movie>>> =
        moviesUseCase.getMovies(page, section, filter).asLiveData()

    fun setSection(section: Int) {
        this.section = section
    }

    fun setMoviesSort(filter: String) {
        _moviesSort.value?.let {
            it[section] = filter
        }
    }
}