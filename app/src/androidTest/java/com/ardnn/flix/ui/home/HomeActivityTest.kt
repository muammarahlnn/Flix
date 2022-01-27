package com.ardnn.flix.ui.home

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.ardnn.flix.R
import com.ardnn.flix.data.source.local.entity.FilmEntity
import com.ardnn.flix.data.source.local.entity.GenreEntity
import com.ardnn.flix.utils.DataDummy
import com.ardnn.flix.utils.EspressoIdlingResource
import com.ardnn.flix.utils.FilmsData
import com.ardnn.flix.utils.Helper
import org.hamcrest.CoreMatchers
import org.junit.After

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {

    private val dummyMovies = DataDummy.generateDummyMovies()
    private val movieId = dummyMovies[0].id
    private val dummyMovieDetail = DataDummy.generateDummyMovieDetail(movieId)

    private val dummyTvShows = DataDummy.generateDummyTvShows()
    private val tvShowId = dummyTvShows[0].id
    private val dummyTvShowDetail = DataDummy.generateDummyTvShowDetail(tvShowId)


    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadFilms() {
        onView(withId(R.id.rvFilm)).check(matches(isDisplayed()))
        onView(withId(R.id.rvFilm)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovies.size))
    }

    @Test
    fun loadTvShows() {
        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.rvFilm)).check(matches(isDisplayed()))
        onView(withId(R.id.rvFilm)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvShows.size))
    }

    @Test
    fun loadMovieDetail() {
        onView(withId(R.id.rvFilm)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.ivWallpaper)).check(matches(isDisplayed()))
        onView(withId(R.id.ivWallpaper)).check(matches(withTagValue(CoreMatchers.equalTo(dummyMovieDetail.wallpaperUrl))))

        onView(withId(R.id.ivPoster)).check(matches(isDisplayed()))
        onView(withId(R.id.ivPoster)).check(matches(withTagValue(CoreMatchers.equalTo(dummyMovieDetail.posterUrl))))

        onView(withId(R.id.tvTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitle)).check(matches(withText(dummyMovieDetail.title)))

        onView(withId(R.id.tvReleaseDate)).check(matches(isDisplayed()))
        onView(withId(R.id.tvReleaseDate)).check(matches(withText(Helper.convertToDate(dummyMovieDetail.releaseDate))))

        onView(withId(R.id.tvRuntime)).check(matches(isDisplayed()))
        onView(withId(R.id.tvRuntime)).check(matches(withText("${dummyMovieDetail.runtime} mins")))

        onView(withId(R.id.tvRating)).check(matches(isDisplayed()))
        onView(withId(R.id.tvRating)).check(matches(withText(dummyMovieDetail.rating.toString())))

        val genreList = dummyMovieDetail.genreList as List<GenreEntity>
        onView(withId(R.id.rvGenre)).check(matches(isDisplayed()))
        onView(withId(R.id.rvGenre)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(genreList.size))

        onView(withId(R.id.tvSynopsis)).check(matches(isDisplayed()))
        onView(withId(R.id.tvSynopsis)).check(matches(withText(dummyMovieDetail.overview)))

        onView(withId(R.id.clWrapperSynopsis)).perform(click()) // to extended synopsis text
        onView(withId(R.id.clWrapperSynopsis)).perform(click()) // to shortened synopsis text
        onView(withId(R.id.btnBack)).perform(click())
    }

    @Test
    fun loadTvShowDetail() {
        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.rvFilm)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.ivWallpaper)).check(matches(isDisplayed()))
        onView(withId(R.id.ivWallpaper)).check(matches(withTagValue(CoreMatchers.equalTo(dummyTvShowDetail.wallpaperUrl))))

        onView(withId(R.id.ivPoster)).check(matches(isDisplayed()))
        onView(withId(R.id.ivPoster)).check(matches(withTagValue(CoreMatchers.equalTo(dummyTvShowDetail.posterUrl))))

        onView(withId(R.id.tvTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitle)).check(matches(withText(dummyTvShowDetail.title)))

        onView(withId(R.id.tvEpisodes)).check(matches(isDisplayed()))
        onView(withId(R.id.tvEpisodes)).check(matches(withText(dummyTvShowDetail.numberOfEpisodes.toString())))

        onView(withId(R.id.tvSeasons)).check(matches(isDisplayed()))
        onView(withId(R.id.tvSeasons)).check(matches(withText(dummyTvShowDetail.numberOfSeasons.toString())))

        val runtime = dummyTvShowDetail.runtimes?.get(0)
        onView(withId(R.id.tvRuntime)).check(matches(isDisplayed()))
        onView(withId(R.id.tvRuntime)).check(matches(withText("$runtime mins")))

        onView(withId(R.id.tvRating)).check(matches(isDisplayed()))
        onView(withId(R.id.tvRating)).check(matches(withText(dummyTvShowDetail.rating.toString())))

        val genreList = dummyTvShowDetail.genreList as List<GenreEntity>
        onView(withId(R.id.rvGenre)).check(matches(isDisplayed()))
        onView(withId(R.id.rvGenre)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(genreList.size))

        onView(withId(R.id.tvFirstAiring)).check(matches(isDisplayed()))
        onView(withId(R.id.tvFirstAiring)).check(matches(withText(Helper.convertToDate(dummyTvShowDetail.firstAirDate))))

        onView(withId(R.id.tvLastAiring)).check(matches(isDisplayed()))
        onView(withId(R.id.tvLastAiring)).check(matches(withText(Helper.convertToDate(dummyTvShowDetail.lastAirDate))))

        onView(withId(R.id.tvSynopsis)).check(matches(isDisplayed()))
        onView(withId(R.id.tvSynopsis)).check(matches(withText(dummyTvShowDetail.overview)))

        onView(withId(R.id.clWrapperSynopsis)).perform(click()) // to extended synopsis text
        onView(withId(R.id.clWrapperSynopsis)).perform(click()) // to shortened synopsis text
        onView(withId(R.id.btnBack)).perform(click())
    }

//    @Test
//    fun loadNullFilm() {
//        onView(withId(R.id.rvFilm)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
//
//        onView(withId(R.id.ivWallpaper)).check(matches(isDisplayed()))
//        onView(withId(R.id.ivWallpaper)).check(matches(withTagValue(CoreMatchers.equalTo(R.drawable.ic_error))))
//
//        onView(withId(R.id.ivPoster)).check(matches(isDisplayed()))
//        onView(withId(R.id.ivPoster)).check(matches(withTagValue(CoreMatchers.equalTo(R.drawable.ic_error))))
//
//        onView(withId(R.id.tvTitle)).check(matches(isDisplayed()))
//        onView(withId(R.id.tvTitle)).check(matches(withText("-")))
//
//        onView(withId(R.id.tvReleaseDate)).check(matches(isDisplayed()))
//        onView(withId(R.id.tvReleaseDate)).check(matches(withText("-")))
//
//        onView(withId(R.id.tvRuntime)).check(matches(isDisplayed()))
//        onView(withId(R.id.tvRuntime)).check(matches(withText("-")))
//
//        onView(withId(R.id.tvRating)).check(matches(isDisplayed()))
//        onView(withId(R.id.tvRating)).check(matches(withText("-")))
//
//        onView(withId(R.id.tvSynopsis)).check(matches(isDisplayed()))
//        onView(withId(R.id.tvSynopsis)).check(matches(withText("-")))
//
//        onView(withId(R.id.clWrapperSynopsis)).perform(click()) // to extended synopsis text
//        onView(withId(R.id.clWrapperSynopsis)).perform(click()) // to shortened synopsis text
//        onView(withId(R.id.btnBack)).perform(click())
//    }
}