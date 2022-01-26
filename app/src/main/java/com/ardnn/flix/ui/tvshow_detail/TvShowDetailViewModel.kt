package com.ardnn.flix.ui.tvshow_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.TvShowDetailEntity

class TvShowDetailViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    private val _isSynopsisExtended = MutableLiveData(false)
    val isSynopsisExtended: LiveData<Boolean> = _isSynopsisExtended

    private var tvShowId = 0

    fun getTvShowDetail(): LiveData<TvShowDetailEntity> =
        flixRepository.getTvShowDetail(tvShowId)

    fun getIsLoadFailure(): LiveData<Boolean> =
        flixRepository.getIsLoadFailure()

    fun getIsLoading(): LiveData<Boolean> =
        flixRepository.getIsLoading()

    fun setIsSynopsisExtended(flag: Boolean) {
        _isSynopsisExtended.value = flag
    }

    fun setTvShowId(tvShowId: Int) {
        this.tvShowId = tvShowId
    }
}