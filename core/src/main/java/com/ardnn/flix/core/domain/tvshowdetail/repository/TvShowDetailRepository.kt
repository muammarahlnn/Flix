package com.ardnn.flix.core.domain.tvshowdetail.repository

import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.domain.moviedetail.model.Cast
import com.ardnn.flix.core.domain.tvshowdetail.model.TvShowDetail
import kotlinx.coroutines.flow.Flow

interface TvShowDetailRepository {

    fun getTvShowDetail(tvShowId: Int): Flow<Resource<TvShowDetail>>

    fun getTvShowCasts(tvShowId: Int): Flow<Resource<List<Cast>>>

    fun setIsFavoriteTvShow(tvShowDetail: TvShowDetail, state: Boolean)
}