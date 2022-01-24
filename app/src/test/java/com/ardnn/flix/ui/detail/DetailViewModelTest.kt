package com.ardnn.flix.ui.detail

import com.ardnn.flix.data.source.local.entity.FilmEntity
import com.ardnn.flix.utils.FilmsData
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class DetailViewModelTest {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var detailViewModelNull: DetailViewModel
    private lateinit var dummyFilm: FilmEntity
    private lateinit var dummyFilmNull: FilmEntity

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication()
        val filmsData = FilmsData(context)
        dummyFilm = filmsData.movies[0]
        dummyFilmNull = filmsData.movies[1]

        detailViewModel = DetailViewModel(dummyFilm)
        detailViewModelNull = DetailViewModel(dummyFilmNull)
    }

    @Test
    fun getFilm() {
        val film = detailViewModel.film.value as FilmEntity
        assertNotNull(film)
        assertEquals(dummyFilm.title, film.title)
        assertEquals(dummyFilm.overview, film.overview)
        assertEquals(dummyFilm.releaseDate, film.releaseDate)
        assertEquals(dummyFilm.rating as Double, film.rating as Double, 0.0001)
        assertEquals(dummyFilm.runtime, film.runtime)
        assertEquals(dummyFilm.poster, film.poster)
    }

    @Test
    fun getFilmNull() {
        val film = detailViewModelNull.film.value as FilmEntity
        assertNotNull(film)
        assertEquals(dummyFilmNull.title, film.title)
        assertEquals(dummyFilmNull.overview, film.overview)
        assertEquals(dummyFilmNull.releaseDate, film.releaseDate)
        assertEquals(dummyFilmNull.rating ?: 0.0, film.rating ?: 0.0, 0.001)
        assertEquals(dummyFilmNull.runtime, film.runtime)
        assertEquals(dummyFilmNull.poster, film.poster)
    }

    @Test
    fun getIsSynopsisExtended() {
        val isSynopsisExtended = detailViewModel.isSynopsisExtended.value
        assertNotNull(isSynopsisExtended)
        assertEquals(false, isSynopsisExtended)
    }
}