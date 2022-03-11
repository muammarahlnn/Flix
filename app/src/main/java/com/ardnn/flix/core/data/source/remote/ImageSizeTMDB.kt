package com.ardnn.flix.core.data.source.remote

enum class ImageSizeTMDB(private val size: String) {
    W500("w500"),
    W780("w780");

    fun getValue(): String {
        return size
    }
}