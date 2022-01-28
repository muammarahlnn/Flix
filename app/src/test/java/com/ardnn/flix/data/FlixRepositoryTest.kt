package com.ardnn.flix.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ardnn.flix.data.source.remote.RemoteDataSource
import com.ardnn.flix.utils.DataDummy
import com.ardnn.flix.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class FlixRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val flixRepository = FakeFlixRepository(remote)

    private val moviesResponse = DataDummy.generateRemoteDummyMovies()
    private val movieId = moviesResponse[0].id
    private val movieDetailResponse = DataDummy.generateRemoteDummyMovieDetail(movieId)

    private val tvShowsResponse = DataDummy.generateRemoteDummyTvShows()
    private val tvShowId = tvShowsResponse[0].id
    private val tvShowDetailResponse = DataDummy.generateRemoteDummyTvShowDetail(tvShowId)

    private val page = 1 // default page to fetch movies and tv shows


    @Test
    fun getMovies() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadMoviesCallback)
                .onSuccess(moviesResponse)
            null
        }.`when`(remote).getNowPlayingMovies(eq(page), any())

        val moviesEntity = LiveDataTestUtil.getValue(flixRepository.getMovies(page))
        verify(remote).getNowPlayingMovies(eq(page), any())

        assertNotNull(moviesEntity)
        assertEquals(moviesResponse.size.toLong(), moviesEntity.size.toLong())
    }

    @Test
    fun getMovieDetail() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadMovieDetailCallback)
                .onSuccess(movieDetailResponse)
            null
        }.`when`(remote).getMovieDetail(eq(movieId), any())

        val movieDetailEntity = LiveDataTestUtil.getValue(flixRepository.getMovieDetail(movieId))
        verify(remote).getMovieDetail(eq(movieId), any())

        assertNotNull(movieDetailEntity)
        assertNotNull(movieDetailEntity.id)
        assertEquals(movieDetailResponse.id, movieDetailEntity.id)
    }

    @Test
    fun getTvShows() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadTvShowsCallback)
                .onSuccess(tvShowsResponse)
            null
        }.`when`(remote).getOnTheAirTvShows(eq(page), any())

        val tvShowsEntity = LiveDataTestUtil.getValue(flixRepository.getTvShows(page))
        verify(remote).getOnTheAirTvShows(eq(page), any())

        assertNotNull(tvShowsEntity)
        assertEquals(tvShowsResponse.size.toLong(), tvShowsEntity.size.toLong())
    }

    @Test
    fun getTvShowDetail() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadTvShowDetailCallback)
                .onSuccess(tvShowDetailResponse)
            null
        }.`when`(remote).getTvShowDetail(eq(tvShowId), any())

        val tvShowDetailEntity = LiveDataTestUtil.getValue(flixRepository.getTvShowDetail(tvShowId))
        verify(remote).getTvShowDetail(eq(tvShowId), any())

        assertNotNull(tvShowDetailEntity)
        assertNotNull(tvShowDetailEntity.id)
        assertEquals(tvShowDetailResponse.id, tvShowDetailEntity.id)
    }

    @Test
    fun getIsLoadFailure() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadMoviesCallback)
                .onSuccess(moviesResponse)
            null
        }.`when`(remote).getNowPlayingMovies(eq(page), any())
        LiveDataTestUtil.getValue(flixRepository.getMovies(page))

        val isLoadFailure = LiveDataTestUtil.getValue(flixRepository.getIsLoadFailure())
        assertNotNull(isLoadFailure)
        assertEquals(false, isLoadFailure)
    }

    @Test
    fun getIsLoading() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadMoviesCallback)
                .onSuccess(moviesResponse)
            null
        }.`when`(remote).getNowPlayingMovies(eq(page), any())
        LiveDataTestUtil.getValue(flixRepository.getMovies(page))

        val isLoading = LiveDataTestUtil.getValue(flixRepository.getIsLoading())
        assertNotNull(isLoading)
        assertEquals(false, isLoading)
    }
}