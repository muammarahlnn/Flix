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

    private val _isSynopsisExtended = MutableLiveData(false)
    val isSynopsisExtended: LiveData<Boolean> = _isSynopsisExtended

    companion object {
        private const val TAG = "TvShowDetailViewModel"
    }

    init {
        fetchTvShowDetail(tvShowId)
    }

    private fun fetchTvShowDetail(tvShowId: Int) {
        TvShowApiConfig.getTvShowDetail(tvShowId, object : TvShowDetailCallback {
            override fun onSuccess(tvShowDetailResponse: TvShowDetailResponse) {
                _tvShowDetail.value = tvShowDetailResponse
            }

            override fun onFailure(message: String) {
                Log.d(TAG, message)
            }
        })
    }

    fun setIsSynopsisExtended(flag: Boolean) {
        _isSynopsisExtended.value = flag
    }
}