package com.ardnn.flix.core.domain.genre.usecase

import com.ardnn.flix.core.domain.genre.model.GenreMovies
import com.ardnn.flix.core.domain.genre.model.GenreTvShows
import kotlinx.coroutines.flow.Flow

interface GenreUseCase {

    fun getGenreWithMovies(genreId: Int): Flow<GenreMovies>

    fun getGenreWithTvShows(genreId: Int): Flow<GenreTvShows>
}