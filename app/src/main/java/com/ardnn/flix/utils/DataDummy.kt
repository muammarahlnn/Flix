package com.ardnn.flix.utils

import android.content.Context
import com.ardnn.flix.data.source.local.entity.*
import com.ardnn.flix.data.source.remote.response.*
import org.json.JSONArray
import org.json.JSONObject

class DataDummy(private val context: Context) {

    private fun parsingFileToString(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use {
            it.readText()
        }
    }

    private fun parsingGenreEntityList(genreArr: JSONArray): List<GenreEntity> {
        val genreList = ArrayList<GenreEntity>()
        for (i in 0 until genreArr.length()) {
            val genre = genreArr.getJSONObject(i)
            val genreEntity = GenreEntity(
                genre.getInt("id"),
                genre.getString("name")
            )

            genreList.add(genreEntity)
        }
        return genreList
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

            val movieEntity = MovieEntity(id, title, releaseDate, posterUrl, rating)
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

    fun generateDummyMovieDetail(): MovieDetailEntity {
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
        val genreList = parsingGenreEntityList(genreArr)

        return MovieDetailEntity(
            id, title, overview, releaseDate, runtime, rating,
            posterUrl, wallpaperUrl, genreList
        )
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

            val tvShowEntity = TvShowEntity(id, title, firstAirDate, posterUrl, rating)
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

    fun generateDummyTvShowDetail(): TvShowDetailEntity {
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
        val genreList = parsingGenreEntityList(genreArr)

        return TvShowDetailEntity(
            id, title, overview, firstAirDate, lastAirDate, runtimeList, rating,
            posterUrl, wallpaperUrl, numberOfEpisodes, numberOfSeasons, genreList
        )
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
}