package com.ardnn.flix.ui.tvshow_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ardnn.flix.api.callback.TvShowDetailCallback
import com.ardnn.flix.api.config.TvShowApiConfig
import com.ardnn.flix.api.response.TvShowDetailResponse

class TvShowDetailViewModel(tvShowId: Int) : ViewModel() {

    private val _tvShowDetail = MutableLiveData<TvShowDetailResponse>()
    val tvShowDetail: LiveData<TvShowDetailResponse> = _tvShowDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isFailure = MutableLiveData<Boolean>()
    val isFailure: LiveData<Boolean> = _isFailure

    private val _isSynopsisExtended = MutableLiveData(false)
    val isSynopsisExtended: LiveData<Boolean> = _isSynopsisExtended

    companion object {
        private const val TAG = "TvShowDetailViewModel"
    }

    init {
        fetchTvShowDetail(tvShowId)
    }

    private fun fetchTvShowDetail(tvShowId: Int) {
        // show progressbar
        _isLoading.value = true

        TvShowApiConfig.getTvShowDetail(tvShowId, object : TvShowDetailCallback {
            override fun onSuccess(tvShowDetailResponse: TvShowDetailResponse) {
                // hide progressbar
                _isLoading.value = false

                // success fetch data
                _isFailure.value = false

                // set tv show detail
                _tvShowDetail.value = tvShowDetailResponse
            }

            override fun onFailure(message: String) {
                // hide progressbar
                _isLoading.value = false

                // unable to fetch data
                _isFailure.value = true

                Log.d(TAG, message)
            }
        })
    }

    fun setIsSynopsisExtended(flag: Boolean) {
        _isSynopsisExtended.value = flag
    }
}