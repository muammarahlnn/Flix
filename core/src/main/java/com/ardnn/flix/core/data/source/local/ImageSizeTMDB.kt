package com.ardnn.flix.core.data.source.local

enum class ImageSizeTMDB(private val size: String) {
    W500("w500"),
    W780("w780");

    fun getValue(): String {
        return size
    }

    companion object {
        const val IMG_URL = "https://image.tmdb.org/t/p/"
    }
}