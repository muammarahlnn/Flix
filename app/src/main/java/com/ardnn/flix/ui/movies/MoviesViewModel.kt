package com.ardnn.flix.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.vo.Resource

class MoviesViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    val section = MutableLiveData<Int>()

    fun getMovies(page: Int): LiveData<Resource<List<MovieEntity>>> =
        Transformations.switchMap(section) { mSection ->
            flixRepository.getMovies(page, mSection)
        }

    fun setSection(section: Int) {
        this.section.value = section
    }
}