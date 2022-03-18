package com.ardnn.flix.core.domain.tvshows.repository

import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.domain.tvshows.model.TvShow
import kotlinx.coroutines.flow.Flow

interface TvShowsRepository {

    fun getTvShows(page: Int, section: Int, filter: String): Flow<Resource<List<TvShow>>>
}