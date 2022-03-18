package com.ardnn.flix.core.domain.genre.usecase

import com.ardnn.flix.core.domain.genre.model.GenreMovies
import com.ardnn.flix.core.domain.genre.model.GenreTvShows
import com.ardnn.flix.core.domain.genre.repository.GenreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenreInteractor @Inject constructor(
    private val genreRepository: GenreRepository
) : GenreUseCase {

    override fun getGenreWithMovies(genreId: Int): Flow<GenreMovies> =
        genreRepository.getGenreWithMovies(genreId)

    override fun getGenreWithTvShows(genreId: Int): Flow<GenreTvShows> =
        genreRepository.getGenreWithTvShows(genreId)
}