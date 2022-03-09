package com.ardnn.flix.ui.tvshowdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ardnn.flix.data.FlixRepository
import com.ardnn.flix.data.source.local.entity.relation.TvShowWithGenres
import com.ardnn.flix.util.DataDummy
import com.ardnn.flix.vo.Resource
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
class TvShowDetailViewModelTest {

    private lateinit var viewModel: TvShowDetailViewModel
    private lateinit var dataDummy: DataDummy
    private var tvShowId = 0

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var flixRepository: FlixRepository

    @Mock
    private lateinit var tvShowDetailObserver: Observer<Resource<TvShowWithGenres>>

    @Mock
    private lateinit var isSynopsisExtendedObserver: Observer<Boolean>

    @Before
    fun setUp() {
        val context = RuntimeEnvironment.getApplication()
        dataDummy = DataDummy(context)

        val dummyTvShows = dataDummy.generateDummyTvShows()
        tvShowId = dummyTvShows[0].id

        viewModel = TvShowDetailViewModel(flixRepository)
        viewModel.setTvShowId(tvShowId)
    }

    @Test
    fun `getTvShowDetail should be success`() {
        val expected = MutableLiveData<Resource<TvShowWithGenres>>()
        expected.value = Resource.success(dataDummy.generateDummyTvShowDetailWithGenres())

        `when`(flixRepository.getTvShowDetailWithGenres(tvShowId))
            .thenReturn(expected)

        viewModel.tvShowDetail.observeForever(tvShowDetailObserver)
        verify(tvShowDetailObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.tvShowDetail.value
        assertEquals(expectedValue, actualValue)
    }

    @Test
    fun `setIsFavorite should be success trigger tvShowDetail observer`() {
        val expected = MutableLiveData<Resource<TvShowWithGenres>>()
        expected.value = Resource.success(dataDummy.generateDummyTvShowDetailWithGenres())

        `when`(flixRepository.getTvShowDetailWithGenres(tvShowId))
            .thenReturn(expected)

        viewModel.setIsFavorite()
        viewModel.tvShowDetail.observeForever(tvShowDetailObserver)
        verify(tvShowDetailObserver).onChanged(expected.value)

        val expectedValue = expected.value
        val actualValue = viewModel.tvShowDetail.value
        assertEquals(expectedValue, actualValue)
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