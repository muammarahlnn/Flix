package com.ardnn.flix.utils

import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.data.source.local.entity.TvShowEntity

interface SingleClickListener<T> {
    fun onItemClicked(item: T)
}