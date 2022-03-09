package com.ardnn.flix.ui.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.relation.SectionWithTvShows
import com.ardnn.flix.vo.Resource

class TvShowsViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    private var section = 0

    private var sortArr = arrayOf("", "", "", "")
    private val _tvShowsSort = MutableLiveData(sortArr)
    val tvShowsSort: LiveData<Array<String>> = _tvShowsSort

    fun getSectionWithTvShows(page: Int, filter: String): LiveData<Resource<SectionWithTvShows>> =
        flixRepository.getSectionWithTvShows(page, section, filter)

    fun setSection(section: Int) {
        this.section = section
    }

    fun setTvShowsSort(filter: String) {
        _tvShowsSort.value?.let {
            it[section] = filter
        }
    }
}