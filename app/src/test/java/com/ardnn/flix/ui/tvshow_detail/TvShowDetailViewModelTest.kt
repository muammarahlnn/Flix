package com.ardnn.flix.ui.tvshow_detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.TvShowDetailEntity
import com.ardnn.flix.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowDetailViewModelTest {

    private lateinit var viewModel: TvShowDetailViewModel
    private val dummyTvShows = DataDummy.generateDummyTvShows()
    private val tvShowId = dummyTvShows[0].id
    private val dummyTvShowDetail = DataDummy.generateDummyTvShowDetail(tvShowId)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var flixRepository: FlixRepository

    @Mock
    private lateinit var tvShowDetailObserver: Observer<TvShowDetailEntity>

    @Mock
    private lateinit var isLoadFailureObserver: Observer<Boolean>

    @Mock
    private lateinit var isLoadingObserver: Observer<Boolean>

    @Mock
    private lateinit var isSynopsisExtendedObserver: Observer<Boolean>

    @Before
    fun setUp() {
        viewModel = TvShowDetailViewModel(flixRepository)
        viewModel.setTvShowId(tvShowId)
    }

    @Test
    fun getTvShowDetail() {
        val tvShowDetail = MutableLiveData<TvShowDetailEntity>()
        tvShowDetail.value = dummyTvShowDetail

        `when`(flixRepository.getTvShowDetail(tvShowId))
            .thenReturn(tvShowDetail)

        val tvShowDetailEntity = viewModel.getTvShowDetail().value
        verify(flixRepository).getTvShowDetail(tvShowId)

        assertNotNull(tvShowDetailEntity)
        assertEquals(dummyTvShowDetail.id, tvShowDetailEntity?.id)
        assertEquals(dummyTvShowDetail.title, tvShowDetailEntity?.title)
        assertEquals(dummyTvShowDetail.overview, tvShowDetailEntity?.overview)
        assertEquals(dummyTvShowDetail.firstAirDate, tvShowDetailEntity?.firstAirDate)
        assertEquals(dummyTvShowDetail.lastAirDate, tvShowDetailEntity?.lastAirDate)
        assertEquals(dummyTvShowDetail.runtimes, tvShowDetailEntity?.runtimes)
        assertEquals(dummyTvShowDetail.rating, tvShowDetailEntity?.rating)
        assertEquals(dummyTvShowDetail.posterUrl, tvShowDetailEntity?.posterUrl)
        assertEquals(dummyTvShowDetail.wallpaperUrl, tvShowDetailEntity?.wallpaperUrl)
        assertEquals(dummyTvShowDetail.numberOfEpisodes, tvShowDetailEntity?.numberOfEpisodes)
        assertEquals(dummyTvShowDetail.numberOfSeasons, tvShowDetailEntity?.numberOfSeasons)
        assertEquals(dummyTvShowDetail.genreList, tvShowDetailEntity?.genreList)

        viewModel.getTvShowDetail().observeForever(tvShowDetailObserver)
        verify(tvShowDetailObserver).onChanged(dummyTvShowDetail)
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