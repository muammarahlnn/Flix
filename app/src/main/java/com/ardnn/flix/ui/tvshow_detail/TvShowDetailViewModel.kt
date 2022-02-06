package com.ardnn.flix.ui.tvshow_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.TvShowDetailEntity
import com.ardnn.flix.vo.Resource

class TvShowDetailViewModel(private val flixRepository: FlixRepository) : ViewModel() {

    val tvShowId = MutableLiveData<Int>()

    var tvShowDetail: LiveData<Resource<TvShowDetailEntity>> =
        Transformations.switchMap(tvShowId) { mTvShowId ->
            flixRepository.getTvShowDetail(mTvShowId)
        }

    private val _isSynopsisExtended = MutableLiveData(false)
    val isSynopsisExtended: LiveData<Boolean> = _isSynopsisExtended

    fun setTvShowId(tvShowId: Int) {
        this.tvShowId.value = tvShowId
    }

    fun setIsSynopsisExtended(flag: Boolean) {
        _isSynopsisExtended.value = flag
    }

    fun setIsFavorite() {
        val tvShowDetailResource = tvShowDetail.value
        if (tvShowDetailResource != null) {
            val tvShowDetailEntity = tvShowDetailResource.data
            if (tvShowDetailEntity != null) {
                val newState = !tvShowDetailEntity.isFavorite
                flixRepository.setIsFavoriteTvShowDetail(tvShowDetailEntity, newState)
            }
        }
    }

}