package com.ardnn.flix.ui.movie_detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.MovieDetailEntity
import com.ardnn.flix.data.source.local.entity.MovieEntity
import com.ardnn.flix.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
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
class MovieDetailViewModelTest {

    private lateinit var viewModel: MovieDetailViewModel
    private var movieId = 0
    private lateinit var dummyMovies: List<MovieEntity>
    private lateinit var dummyMovieDetail: MovieDetailEntity

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var flixRepository: FlixRepository

    @Mock
    private lateinit var movieDetailObserver: Observer<MovieDetailEntity>

    @Mock
    private lateinit var isLoadFailureObserver: Observer<Boolean>

    @Mock
    private lateinit var isLoadingObserver: Observer<Boolean>

    @Mock
    private lateinit var isSynopsisExtendedObserver: Observer<Boolean>

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication()
        val dataDummy = DataDummy(context)

        dummyMovies = dataDummy.generateDummyMovies()
        movieId = dummyMovies[0].id
        dummyMovieDetail = dataDummy.generateDummyMovieDetail()

        viewModel = MovieDetailViewModel(flixRepository)
        viewModel.setMovieId(movieId)
    }

    @Test
    fun getMovieDetail() {
        val movieDetail = MutableLiveData<MovieDetailEntity>()
        movieDetail.value = dummyMovieDetail

        `when`(flixRepository.getMovieDetail(movieId))
            .thenReturn(movieDetail)

        val movieDetailEntity = viewModel.getMovieDetail().value
        verify(flixRepository).getMovieDetail(movieId)

        assertNotNull(movieDetailEntity)
        assertEquals(dummyMovieDetail.id, movieDetailEntity?.id)
        assertEquals(dummyMovieDetail.title, movieDetailEntity?.title)
        assertEquals(dummyMovieDetail.overview, movieDetailEntity?.overview)
        assertEquals(dummyMovieDetail.releaseDate, movieDetailEntity?.releaseDate)
        assertEquals(dummyMovieDetail.runtime, movieDetailEntity?.runtime)
        assertEquals(dummyMovieDetail.rating, movieDetailEntity?.rating)
        assertEquals(dummyMovieDetail.posterUrl, movieDetailEntity?.posterUrl)
        assertEquals(dummyMovieDetail.wallpaperUrl, movieDetailEntity?.wallpaperUrl)
        assertEquals(dummyMovieDetail.genreList, movieDetailEntity?.genreList)

        viewModel.getMovieDetail().observeForever(movieDetailObserver)
        verify(movieDetailObserver).onChanged(dummyMovieDetail)
    }

    @Test
    fun getIsLoadFailure() {
        val dummyIsLoadFailure = MutableLiveData<Boolean>()
        dummyIsLoadFailure.value = false

        `when`(flixRepository.getIsLoadFailure())
            .thenReturn(dummyIsLoadFailure)

        val isLoadFailure = viewModel.getIsLoadFailure().value
        verify(flixRepository).getIsLoadFailure()

        assertNotNull(isLoadFailure)
        assertEquals(false, isLoadFailure)

        viewModel.getIsLoadFailure().observeForever(isLoadFailureObserver)
        verify(isLoadFailureObserver).onChanged(false)
    }

    @Test
    fun getIsLoading() {
        val dummyIsLoading = MutableLiveData<Boolean>()
        dummyIsLoading.value = false

        `when`(flixRepository.getIsLoading())
            .thenReturn(dummyIsLoading)

        val isLoading = viewModel.getIsLoading().value
        verify(flixRepository).getIsLoading()

        assertNotNull(isLoading)
        assertEquals(false, isLoading)

        viewModel.getIsLoading().observeForever(isLoadingObserver)
        verify(isLoadingObserver).onChanged(false)
    }

    @Test
    fun getIsSynopsisExtended() {
        val isSynopsisExtended = viewModel.isSynopsisExtended.value
        assertNotNull(isSynopsisExtended)
        assertEquals(false, isSynopsisExtended)

        viewModel.isSynopsisExtended.observeForever(isSynopsisExtendedObserver)
        verify(isSynopsisExtendedObserver).onChanged(isSynopsisExtended)
    }
}