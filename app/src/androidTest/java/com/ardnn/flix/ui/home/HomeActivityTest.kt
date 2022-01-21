package com.ardnn.flix.ui.home

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.ardnn.flix.R
import com.ardnn.flix.data.FilmEntity
import com.ardnn.flix.utils.FilmsData
import org.hamcrest.CoreMatchers

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {

    private lateinit var movies: List<FilmEntity>
    private lateinit var tvShows: List<FilmEntity>

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val filmsData = FilmsData(context)

        movies = filmsData.movies
        tvShows = filmsData.tvShows
    }

    @Test
    fun loadFilms() {
        onView(withId(R.id.rvFilm)).check(matches(isDisplayed()))
        onView(withId(R.id.rvFilm)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(movies.size))
    }

    @Test
    fun loadTvShows() {
        onView(withText("TV Shows")).perform(click())
        onView(withId(R.id.rvFilm)).check(matches(isDisplayed()))
        onView(withId(R.id.rvFilm)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(tvShows.size))
    }

    @Test
    fun loadDetailFilm() {
        onView(withId(R.id.rvFilm)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        val movie = movies[0]
        onView(withId(R.id.ivWallpaper)).check(matches(isDisplayed()))
        onView(withId(R.id.ivWallpaper)).check(matches(withTagValue(CoreMatchers.equalTo(movie.poster))))

        onView(withId(R.id.ivPoster)).check(matches(isDisplayed()))
        onView(withId(R.id.ivPoster)).check(matches(withTagValue(CoreMatchers.equalTo(movie.poster))))

        onView(withId(R.id.tvTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitle)).check(matches(withText(movie.title)))

        onView(withId(R.id.tvReleaseDate)).check(matches(isDisplayed()))
        onView(withId(R.id.tvReleaseDate)).check(matches(withText(movie.releaseDate)))

        onView(withId(R.id.tvRuntime)).check(matches(isDisplayed()))
        onView(withId(R.id.tvRuntime)).check(matches(withText("${movie.runtime} mins")))

        onView(withId(R.id.tvRating)).check(matches(isDisplayed()))
        onView(withId(R.id.tvRating)).check(matches(withText(movie.rating.toString())))

        onView(withId(R.id.tvSynopsis)).check(matches(isDisplayed()))
        onView(withId(R.id.tvSynopsis)).check(matches(withText(movie.overview)))

        onView(withId(R.id.clWrapperSynopsis)).perform(click()) // to extended synopsis text
        onView(withId(R.id.clWrapperSynopsis)).perform(click()) // to shortened synopsis text
        onView(withId(R.id.btnBack)).perform(click())

    }

    @Test
    fun loadNullFilm() {
        onView(withId(R.id.rvFilm)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))

        onView(withId(R.id.ivWallpaper)).check(matches(isDisplayed()))
        onView(withId(R.id.ivWallpaper)).check(matches(withTagValue(CoreMatchers.equalTo(R.drawable.ic_error))))

        onView(withId(R.id.ivPoster)).check(matches(isDisplayed()))
        onView(withId(R.id.ivPoster)).check(matches(withTagValue(CoreMatchers.equalTo(R.drawable.ic_error))))

        onView(withId(R.id.tvTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitle)).check(matches(withText("-")))

        onView(withId(R.id.tvReleaseDate)).check(matches(isDisplayed()))
        onView(withId(R.id.tvReleaseDate)).check(matches(withText("-")))

        onView(withId(R.id.tvRuntime)).check(matches(isDisplayed()))
        onView(withId(R.id.tvRuntime)).check(matches(withText("-")))

        onView(withId(R.id.tvRating)).check(matches(isDisplayed()))
        onView(withId(R.id.tvRating)).check(matches(withText("-")))

        onView(withId(R.id.tvSynopsis)).check(matches(isDisplayed()))
        onView(withId(R.id.tvSynopsis)).check(matches(withText("-")))

        onView(withId(R.id.clWrapperSynopsis)).perform(click()) // to extended synopsis text
        onView(withId(R.id.clWrapperSynopsis)).perform(click()) // to shortened synopsis text
        onView(withId(R.id.btnBack)).perform(click())
    }
}