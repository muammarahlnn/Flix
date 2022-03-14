package com.ardnn.flix.core.data.source.remote.datasource

import com.ardnn.flix.core.data.source.remote.ApiResponse
import kotlinx.coroutines.flow.Flow

interface DetailInterface<T> {
    fun getDetail(id: Int): Flow<ApiResponse<T>>
}