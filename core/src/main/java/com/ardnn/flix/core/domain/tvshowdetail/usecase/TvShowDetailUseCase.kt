package com.ardnn.flix.core.domain.tvshowdetail.usecase

import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.domain.moviedetail.model.Cast
import com.ardnn.flix.core.domain.tvshowdetail.model.TvShowDetail
import kotlinx.coroutines.flow.Flow

interface TvShowDetailUseCase {

    fun getTvShowDetail(tvShowId: Int): Flow<Resource<TvShowDetail>>

    fun getTvShowCasts(tvShowId: Int): Flow<Resource<List<Cast>>>

    fun setIsFavoriteTvShow(tvShow: TvShowDetail, state: Boolean)
}