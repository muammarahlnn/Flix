package com.ardnn.flix.ui.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.TvShowEntity
import com.ardnn.flix.vo.Resource

class TvShowsViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    val section = MutableLiveData<Int>()

    fun getTvShows(page: Int): LiveData<Resource<PagedList<TvShowEntity>>> =
        Transformations.switchMap(section) { mSection ->
            flixRepository.getTvShows(page, mSection)
        }

    fun setSection(section: Int) {
        this.section.value = section
    }
}