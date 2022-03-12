package com.ardnn.flix.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.core.data.FlixRepositoryImpl
import com.ardnn.flix.core.domain.model.Movie
import com.ardnn.flix.core.domain.usecase.FlixUseCase
import com.ardnn.flix.core.vo.Resource

class MoviesViewModel(private val flixUseCase: FlixUseCase) : ViewModel() {

    private var section = 0

    private val sortArr = arrayOf("", "", "", "")
    private val _moviesSort = MutableLiveData(sortArr)
    val moviesSort: LiveData<Array<String>> = _moviesSort

    fun getSectionWithMovies(page: Int, filter: String): LiveData<Resource<List<Movie>>> =
        flixUseCase.getSectionWithMovies(page, section, filter)

    fun setSection(section: Int) {
        this.section = section
    }

    fun setMoviesSort(filter: String) {
        _moviesSort.value?.let {
            it[section] = filter
        }
    }
}