package com.ardnn.flix.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.relation.SectionWithMovies
import com.ardnn.flix.vo.Resource

class MoviesViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    private var section = 0

    private val sortArr = arrayOf("", "", "", "")
    private val _moviesSort = MutableLiveData(sortArr)
    val moviesSort: LiveData<Array<String>> = _moviesSort

    fun getSectionWithMovies(page: Int, filter: String): LiveData<Resource<SectionWithMovies>> =
        flixRepository.getSectionWithMovies(page, section, filter)

    fun setSection(section: Int) {
        this.section = section
    }

    fun setMoviesSort(filter: String) {
        _moviesSort.value?.let {
            it[section] = filter
        }
    }
}