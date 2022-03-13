package com.ardnn.flix.core.data.source.remote.datasource

import com.ardnn.flix.core.data.source.remote.ApiResponse
import io.reactivex.Flowable

interface DetailInterface<T> {
    fun getDetail(id: Int): Flowable<ApiResponse<T>>
}