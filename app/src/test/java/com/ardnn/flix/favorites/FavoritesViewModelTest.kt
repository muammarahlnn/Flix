package com.ardnn.flix.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.ardnn.flix.core.data.FlixRepositoryImpl
import com.ardnn.flix.core.data.source.local.entity.MovieEntity
import com.ardnn.flix.core.data.source.local.entity.TvShowEntity
import com.ardnn.flix.core.util.DataDummy
import com.ardnn.flix.core.util.PagedTestDataSources
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class FavoritesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var flixRepository: FlixRepositoryImpl

    @Mock
    private lateinit var favoriteMoviesObserver: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var favoriteTvShowsObserver: Observer<PagedList<TvShowEntity>>

    private lateinit var viewModel: com.ardnn.flix.favorites.FavoritesViewModel
    private lateinit var dataDummy: DataDummy

    @Before
    fun setUp() {
        viewModel = com.ardnn.flix.favorites.FavoritesViewModel(flixRepository)

        val context = RuntimeEnvironment.getApplication()
        dataDummy = DataDummy(context)
    }

    @Test
    fun `getFavoriteMovies should be success`() {
        val expected = MutableLiveData<PagedList<MovieEntity>>()
        expected.value = PagedTestDataSources.snapshot(dataDummy.generateDummyMovieDetailList())

        `when`(flixRepository.getFavoriteMovies())
            .thenReturn(expected)

        viewModel.getFavoriteMovies().observeForever(favoriteMoviesObserver)
        verify(favoriteMoviesObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getFavoriteMovies().value
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.snapshot(), actualValue?.snapshot())
        assertEquals(expectedValue?.size, actualValue?.size)
    }

    @Test
    fun `getFavoriteMovies should be success but data is empty`() {
        val expected = MutableLiveData<PagedList<MovieEntity>>()
        expected.value = PagedTestDataSources.snapshot()

        `when`(flixRepository.getFavoriteMovies())
            .thenReturn(expected)

        viewModel.getFavoriteMovies().observeForever(favoriteMoviesObserver)
        verify(favoriteMoviesObserver).onChanged(expected.value)

        val actualValueDataSize = viewModel.getFavoriteMovies().value?.size
        assertTrue("size of data should be 0, actual is $actualValueDataSize", actualValueDataSize == 0)
    }

    @Test
    fun `getFavoriteTvShows should be success`() {
        val expected = MutableLiveData<PagedList<TvShowEntity>>()
        expected.value = PagedTestDataSources.snapshot(dataDummy.generateDummyTvShowDetailList())

        `when`(flixRepository.getFavoriteTvShows())
            .thenReturn(expected)

        viewModel.getFavoriteTvShows().observeForever(favoriteTvShowsObserver)
        verify(favoriteTvShowsObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.getFavoriteTvShows().value
        assertEquals(expectedValue, actualValue)
        assertEquals(expectedValue?.snapshot(), actualValue?.snapshot())
        assertEquals(expectedValue?.size, actualValue?.size)
    }

    @Test
    fun `getFavoriteTvShows should be success but data is empty`() {
        val expected = MutableLiveData<PagedList<TvShowEntity>>()
        expected.value = PagedTestDataSources.snapshot()

        `when`(flixRepository.getFavoriteTvShows())
            .thenReturn(expected)

        viewModel.getFavoriteTvShows().observeForever(favoriteTvShowsObserver)
        verify(favoriteTvShowsObserver).onChanged(expected.value)

        val actualValueDataSize = viewModel.getFavoriteTvShows().value?.size
        assertTrue("size of data should be 0, actual is $actualValueDataSize", actualValueDataSize == 0)
    }

}