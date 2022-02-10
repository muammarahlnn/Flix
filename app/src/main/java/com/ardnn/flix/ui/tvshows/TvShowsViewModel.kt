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

    private var section = 0

    private var sortArr = arrayOf("", "")
    private val _tvShowsSort = MutableLiveData(sortArr)
    val tvShowsSort: LiveData<Array<String>> = _tvShowsSort

    fun getTvShows(page: Int, filter: String): LiveData<Resource<PagedList<TvShowEntity>>> =
        flixRepository.getTvShows(page, section, filter)

    fun setSection(section: Int) {
        this.section = section
    }

    fun setTvShowsSort(filter: String) {
        _tvShowsSort.value?.let {
            it[section] = filter
        }
    }
}