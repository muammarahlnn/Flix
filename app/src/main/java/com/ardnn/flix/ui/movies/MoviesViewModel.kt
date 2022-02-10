package com.ardnn.flix.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.vo.Resource

class MoviesViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    private var section = 0

    private val sortArr = arrayOf("", "")
    private val _moviesSort = MutableLiveData(sortArr)
    val moviesSort: LiveData<Array<String>> = _moviesSort

    fun getMovies(page: Int, filter: String): LiveData<Resource<PagedList<MovieEntity>>> =
        flixRepository.getMovies(page, section, filter)

    fun setSection(section: Int) {
        this.section = section
    }

    fun setMoviesSort(filter: String) {
        _moviesSort.value?.let {
            it[section] = filter
        }
    }
}