package com.ardnn.flix.ui.tvshow_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.data.FlixRepository

class TvShowDetailViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFailure = MutableLiveData<Boolean>()
    val isFailure: LiveData<Boolean> = _isFailure

    private val _isSynopsisExtended = MutableLiveData(false)
    val isSynopsisExtended: LiveData<Boolean> = _isSynopsisExtended

    private var tvShowId = 0

    fun setTvShowId(tvShowId: Int) {
        this.tvShowId = tvShowId
    }

    fun getTvShowDetail() =
        flixRepository.getTvShowDetail(tvShowId)

    fun setIsSynopsisExtended(flag: Boolean) {
        _isSynopsisExtended.value = flag
    }
}