package com.ardnn.flix.core.util

import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import java.util.concurrent.Executors

class PagedTestDataSources <T> private constructor(
    private val items: List<T>
) : PositionalDataSource<T>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        callback.onResult(items, 0, items.size)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
        val start = params.startPosition
        val end = params.startPosition + params.loadSize
        callback.onResult(items.subList(start, end))
    }

    companion object {
        fun <T> snapshot(items: List<T> = listOf()): PagedList<T> {
            return PagedList.Builder(PagedTestDataSources(items), 10)
                .setNotifyExecutor(Executors.newSingleThreadExecutor())
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .build()
        }
    }
}