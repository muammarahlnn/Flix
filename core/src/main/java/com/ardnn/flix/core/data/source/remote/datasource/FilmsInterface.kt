package com.ardnn.flix.core.data.source.remote.datasource

import com.ardnn.flix.core.data.source.remote.ApiResponse
import com.ardnn.flix.core.data.source.remote.response.CastResponse
import kotlinx.coroutines.flow.Flow

interface FilmsInterface<T> {

    fun getFirstSectionFilms(page: Int): Flow<ApiResponse<List<T>>>

    fun getSecondSectionFilms(page: Int): Flow<ApiResponse<List<T>>>

    fun getThirdSectionFilms(page: Int): Flow<ApiResponse<List<T>>>

    fun getFourthSectionFilms(page: Int): Flow<ApiResponse<List<T>>>

    fun getFilmCredits(id: Int): Flow<ApiResponse<List<CastResponse>>>
}