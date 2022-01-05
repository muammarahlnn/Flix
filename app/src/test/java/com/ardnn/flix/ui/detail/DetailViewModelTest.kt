package com.ardnn.flix.ui.detail

import com.ardnn.flix.data.FilmEntity
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
    private lateinit var dummyFilm: FilmEntity

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication()
        val filmsData = FilmsData(context)
        dummyFilm = filmsData.movies[0]

        detailViewModel = DetailViewModel(dummyFilm)
    }

    @Test
    fun getFilm() {
        val film = detailViewModel.film.value
        assertNotNull(film)
        if (film != null) {
            assertEquals(dummyFilm.title, film.title)
            assertEquals(dummyFilm.overview, film.overview)
            assertEquals(dummyFilm.releaseDate, film.releaseDate)
            assertEquals(dummyFilm.rating, film.rating, 0.0001)
            assertEquals(dummyFilm.runtime, film.runtime)
            assertEquals(dummyFilm.poster, film.poster)
        }
    }

    @Test
    fun getIsSynopsisExtended() {
        val isSynopsisExtended = detailViewModel.isSynopsisExtended.value
        assertNotNull(isSynopsisExtended)
        assertEquals(false, isSynopsisExtended)
    }
}