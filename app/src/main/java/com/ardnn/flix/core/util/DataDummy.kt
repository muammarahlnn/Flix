package com.ardnn.flix.core.util

import android.content.Context
import com.ardnn.flix.core.data.source.local.entity.*
import com.ardnn.flix.core.data.source.local.entity.relation.GenreWithMovies
import com.ardnn.flix.core.data.source.local.entity.relation.GenreWithTvShows
import com.ardnn.flix.core.data.source.local.entity.relation.MovieWithGenres
import com.ardnn.flix.core.data.source.local.entity.relation.TvShowWithGenres
import com.ardnn.flix.core.data.source.remote.response.*
import org.json.JSONArray
import org.json.JSONObject

class DataDummy(private val context: Context) {

    private fun parsingFileToString(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
    }

    private fun parsingGenreResponseList(genreArr: JSONArray): List<GenreResponse> {
        val genreList = ArrayList<GenreResponse>()
        for (i in 0 until genreArr.length()) {
            val genre = genreArr.getJSONObject(i)
            val genreResponse = GenreResponse(
                genre.getInt("id"),
                genre.getString("name")
            )

            genreList.add(genreResponse)
        }
        return genreList
    }

    fun generateDummyMovies(): List<MovieEntity> {
        val moviesData = parsingFileToString("movies.json")
        val moviesArr = JSONObject(moviesData).getJSONArray("movies")

        val list = ArrayList<MovieEntity>()
        for (i in 0 until moviesArr.length()) {
            val movie = moviesArr.getJSONObject(i)

            val id = movie.getInt("id")
            val title = movie.getString("title")
            val releaseDate = movie.getString("release_date")
            val posterUrl = movie.getString("poster_path")
            val rating = movie.getDouble("vote_average").toFloat()

            val movieEntity = MovieEntity(
                id = id,
                title = title,
                releaseDate = releaseDate,
                posterUrl = posterUrl,
                rating = rating
            )
            list.add(movieEntity)
        }
        return list
    }

    fun generateRemoteDummyMovies(): List<MovieResponse> {
        val moviesData = parsingFileToString("movies.json")
        val moviesArr = JSONObject(moviesData).getJSONArray("movies")

        val list = ArrayList<MovieResponse>()
        for (i in 0 until moviesArr.length()) {
            val movie = moviesArr.getJSONObject(i)

            val id = movie.getInt("id")
            val title = movie.getString("title")
            val releaseDate = movie.getString("release_date")
            val posterUrl = movie.getString("poster_path")
            val rating = movie.getDouble("vote_average").toFloat()

            val movieResponse = MovieResponse(id, title, releaseDate, posterUrl, rating)
            list.add(movieResponse)
        }
        return list
    }

    fun generateDummyMovieDetail(): MovieEntity {
        val movieDetailData = parsingFileToString("movie_detail.json")
        val movieDetail = JSONObject(movieDetailData)

        val id = movieDetail.getInt("id")
        val title = movieDetail.getString("title")
        val overview = movieDetail.getString("overview")
        val releaseDate = movieDetail.getString("release_date")
        val runtime = movieDetail.getInt("runtime")
        val rating = movieDetail.getDouble("vote_average").toFloat()
        val posterUrl = movieDetail.getString("poster_path")
        val wallpaperUrl = movieDetail.getString("backdrop_path")

        return MovieEntity(
            id = id,
            title = title,
            overview = overview,
            releaseDate = releaseDate,
            runtime = runtime,
            rating = rating,
            posterUrl = posterUrl,
            wallpaperUrl = wallpaperUrl
        )
    }

    fun generateDummyMovieDetailWithGenres(): MovieWithGenres {
        val movieDetail = generateDummyMovieDetail()

        val genres = ArrayList<GenreEntity>()
        for (i in 1..5) {
            val genre = GenreEntity(i, "dummy $i")
            genres.add(genre)
        }

        return MovieWithGenres(movieDetail, genres)
    }

    fun generateRemoteDummyMovieDetail(): MovieDetailResponse {
        val movieDetailData = parsingFileToString("movie_detail.json")
        val movieDetail = JSONObject(movieDetailData)

        val id = movieDetail.getInt("id")
        val title = movieDetail.getString("title")
        val overview = movieDetail.getString("overview")
        val releaseDate = movieDetail.getString("release_date")
        val runtime = movieDetail.getInt("runtime")
        val rating = movieDetail.getDouble("vote_average").toFloat()
        val posterUrl = movieDetail.getString("poster_path")
        val wallpaperUrl = movieDetail.getString("backdrop_path")

        val genreArr = movieDetail.getJSONArray("genres")
        val genreList = parsingGenreResponseList(genreArr)

        return MovieDetailResponse(
            id, title, overview, releaseDate, runtime, rating,
            posterUrl, wallpaperUrl, genreList
        )
    }

    fun generateDummyTvShows(): List<TvShowEntity> {
        val tvShowsData = parsingFileToString("tv_shows.json")
        val tvShowsArr = JSONObject(tvShowsData).getJSONArray("tv_shows")

        val list = ArrayList<TvShowEntity>()
        for (i in 0 until tvShowsArr.length()) {
            val movie = tvShowsArr.getJSONObject(i)

            val id = movie.getInt("id")
            val title = movie.getString("name")
            val firstAirDate = movie.getString("first_air_date")
            val posterUrl = movie.getString("poster_path")
            val rating = movie.getDouble("vote_average").toFloat()

            val tvShowEntity = TvShowEntity(
                id = id,
                title = title,
                firstAirDate = firstAirDate,
                posterUrl = posterUrl,
                rating = rating
            )
            list.add(tvShowEntity)
        }
        return list
    }

    fun generateRemoteDummyTvShows(): List<TvShowResponse> {
        val tvShowsData = parsingFileToString("tv_shows.json")
        val tvShowsArr = JSONObject(tvShowsData).getJSONArray("tv_shows")

        val list = ArrayList<TvShowResponse>()
        for (i in 0 until tvShowsArr.length()) {
            val movie = tvShowsArr.getJSONObject(i)

            val id = movie.getInt("id")
            val title = movie.getString("name")
            val firstAirDate = movie.getString("first_air_date")
            val posterUrl = movie.getString("poster_path")
            val rating = movie.getDouble("vote_average").toFloat()

            val tvShowResponse = TvShowResponse(id, title, firstAirDate, posterUrl, rating)
            list.add(tvShowResponse)
        }
        return list
    }

    fun generateDummyTvShowDetail(): TvShowEntity {
        val tvShowDetailData = parsingFileToString("tv_show_detail.json")
        val tvShowDetail = JSONObject(tvShowDetailData)

        val id = tvShowDetail.getInt("id")
        val title = tvShowDetail.getString("name")
        val overview = tvShowDetail.getString("overview")
        val firstAirDate = tvShowDetail.getString("first_air_date")
        val lastAirDate = tvShowDetail.getString("last_air_date")
        val rating = tvShowDetail.getDouble("vote_average").toFloat()
        val posterUrl = tvShowDetail.getString("poster_path")
        val wallpaperUrl = tvShowDetail.getString("backdrop_path")
        val numberOfEpisodes = tvShowDetail.getInt("number_of_episodes")
        val numberOfSeasons = tvShowDetail.getInt("number_of_seasons")

        val runtimeArr = tvShowDetail.getJSONArray("episode_run_time")
        val runtimeList = ArrayList<Int>()
        for (i in 0 until runtimeArr.length()) {
            val runtime = runtimeArr.getInt(i)
            runtimeList.add(runtime)
        }

        return TvShowEntity(
            id = id,
            title = title,
            overview = overview,
            firstAirDate = firstAirDate,
            lastAirDate = lastAirDate,
            runtime = runtimeList[0],
            rating = rating,
            posterUrl = posterUrl,
            wallpaperUrl = wallpaperUrl,
            numberOfSeasons = numberOfEpisodes,
            numberOfEpisodes = numberOfSeasons
        )
    }

    fun generateDummyTvShowDetailWithGenres(): TvShowWithGenres {
        val tvShowDetail = generateDummyTvShowDetail()

        val genres = ArrayList<GenreEntity>()
        for (i in 1..5) {
            val genre = GenreEntity(i, "dummy $i")
            genres.add(genre)
        }

        return TvShowWithGenres(tvShowDetail, genres)
    }

    fun generateRemoteDummyTvShowDetail(): TvShowDetailResponse {
        val tvShowDetailData = parsingFileToString("tv_show_detail.json")
        val tvShowDetail = JSONObject(tvShowDetailData)

        val id = tvShowDetail.getInt("id")
        val title = tvShowDetail.getString("name")
        val overview = tvShowDetail.getString("overview")
        val firstAirDate = tvShowDetail.getString("first_air_date")
        val lastAirDate = tvShowDetail.getString("last_air_date")
        val rating = tvShowDetail.getDouble("vote_average").toFloat()
        val posterUrl = tvShowDetail.getString("poster_path")
        val wallpaperUrl = tvShowDetail.getString("backdrop_path")
        val numberOfEpisodes = tvShowDetail.getInt("number_of_episodes")
        val numberOfSeasons = tvShowDetail.getInt("number_of_seasons")

        val runtimeArr = tvShowDetail.getJSONArray("episode_run_time")
        val runtimeList = ArrayList<Int>()
        for (i in 0 until runtimeArr.length()) {
            val runtime = runtimeArr.getInt(i)
            runtimeList.add(runtime)
        }

        val genreArr = tvShowDetail.getJSONArray("genres")
        val genreList = parsingGenreResponseList(genreArr)

        return TvShowDetailResponse(
            id, title, overview, firstAirDate, lastAirDate, runtimeList, rating,
            posterUrl, wallpaperUrl, numberOfEpisodes, numberOfSeasons, genreList
        )
    }


    fun generateDummyMovieDetailList(): List<MovieEntity> {
        val list = ArrayList<MovieEntity>()
        for (i in 0 until 20) {
            list.add(generateDummyMovieDetail())
        }
        return list
    }

    fun generateDummyTvShowDetailList(): List<TvShowEntity> {
        val list = ArrayList<TvShowEntity>()
        for (i in 0 until 20) {
            list.add(generateDummyTvShowDetail())
        }
        return list
    }

    fun generateDummyGenreWithMovies(isEmpty: Boolean = false): GenreWithMovies {
        val genre = GenreEntity(0, "dummy")
        val movies =
            if (isEmpty) listOf()
            else generateDummyMovieDetailList()

        return GenreWithMovies(genre, movies)
    }

    fun generateDummyGenreWithTvShows(isEmpty: Boolean = false): GenreWithTvShows {
        val genre = GenreEntity(0, "dummy")
        val tvShows =
            if (isEmpty) listOf()
            else generateDummyTvShowDetailList()

        return GenreWithTvShows(genre, tvShows)
    }
}