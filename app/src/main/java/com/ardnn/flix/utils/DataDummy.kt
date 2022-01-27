package com.ardnn.flix.utils

import com.ardnn.flix.data.source.local.entity.*
import com.ardnn.flix.data.source.remote.response.*

object DataDummy {

    fun generateDummyMovies(): List<MovieEntity> {
        // using 20 data dummy movie to equalize movies response' size from API
        val movies = ArrayList<MovieEntity>()
        movies.add(MovieEntity(
            634649,
            "Spider-Man: No Way Home",
            "2021-12-15",
            "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
            8.5f))
        movies.add(MovieEntity(
            568124,
            "Encanto",
            "2021-11-24",
            "/4j0PNHkMr5ax3IA8tjtxcmPU3QT.jpg",
            7.8f))
        movies.add(MovieEntity(
            3,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieEntity(
            4,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieEntity(
            5,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieEntity(
            6,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieEntity(
            7,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieEntity(
            8,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieEntity(
            9,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieEntity(
            10,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieEntity(
            11,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieEntity(
            12,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieEntity(
            13,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieEntity(
            14,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieEntity(
            15,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieEntity(
            16,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieEntity(
            17,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieEntity(
            18,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieEntity(
            19,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieEntity(
            20,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))

        return movies
    }

    fun generateDummyMovieDetail(movieId: Int): MovieDetailEntity {
        val genreList = arrayListOf(
            GenreEntity(28, "Action"),
            GenreEntity(12, "Adventure"),
            GenreEntity(878, "Science Fiction"),
        )

        return MovieDetailEntity(
            movieId,
            "Spider-Man: No Way Home",
            "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
            "2021-12-15",
            148,
            8.5f,
            "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
            "/1Rr5SrvHxMXHu5RjKpaMba8VTzi.jpg",
            genreList
        )
    }

    fun generateDummyTvShows(): List<TvShowEntity> {
        // using 20 data dummy tv show to equalize tv shows response' size from API
        val tvShows = ArrayList<TvShowEntity>()
        tvShows.add(TvShowEntity(
            85552,
            "Euphoria",
            "2019-06-16",
            "/jtnfNzqZwN4E32FGGxx1YZaBWWf.jpg",
            8.4f,))
        tvShows.add(TvShowEntity(
            2,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowEntity(
            3,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowEntity(
            4,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowEntity(
            5,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowEntity(
            6,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowEntity(
            7,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowEntity(
            8,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowEntity(
            9,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowEntity(
            10,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowEntity(
            11,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowEntity(
            12,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowEntity(
            13,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowEntity(
            14,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowEntity(
            15,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowEntity(
            16,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowEntity(
            17,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowEntity(
            18,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowEntity(
            19,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowEntity(
            20,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))


        return tvShows
    }

    fun generateDummyTvShowDetail(tvShowId: Int): TvShowDetailEntity {
        val genreList = arrayListOf(
            GenreEntity(18, "Drama")
        )
        val runtimes = listOf(60)

        return TvShowDetailEntity(
            tvShowId,
            "Euphoria",
            "A group of high school students navigate love and friendships in a world of drugs, sex, trauma, and social media.",
            "2019-06-16",
            "2022-01-23",
            runtimes,
            8.4f,
            "/jtnfNzqZwN4E32FGGxx1YZaBWWf.jpg",
            "/oKt4J3TFjWirVwBqoHyIvv5IImd.jpg",
            14,
            2,
            genreList
        )
    }

    fun generateRemoteDummyMovies(): List<MovieResponse> {
        // using 20 data dummy movie to equalize movies response' size from API
        val movies = ArrayList<MovieResponse>()
        movies.add(MovieResponse(
            634649,
            "Spider-Man: No Way Home",
            "2021-12-15",
            "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
            8.5f))
        movies.add(MovieResponse(
            568124,
            "Encanto",
            "2021-11-24",
            "/4j0PNHkMr5ax3IA8tjtxcmPU3QT.jpg",
            7.8f))
        movies.add(MovieResponse(
            3,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieResponse(
            4,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieResponse(
            5,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieResponse(
            6,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieResponse(
            7,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieResponse(
            8,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieResponse(
            9,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieResponse(
            10,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieResponse(
            11,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieResponse(
            12,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieResponse(
            13,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieResponse(
            14,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieResponse(
            15,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieResponse(
            16,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieResponse(
            17,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieResponse(
            18,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieResponse(
            19,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        movies.add(MovieResponse(
            20,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))

        return movies
    }

    fun generateRemoteDummyMovieDetail(movieId: Int): MovieDetailResponse {
        val genreList = arrayListOf(
            GenreResponse(28, "Action"),
            GenreResponse(12, "Adventure"),
            GenreResponse(878, "Science Fiction"),
        )

        return MovieDetailResponse(
            movieId,
            "Spider-Man: No Way Home",
            "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
            "2021-12-15",
            148,
            8.5f,
            "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg",
            "/1Rr5SrvHxMXHu5RjKpaMba8VTzi.jpg",
            genreList
        )
    }

    fun generateRemoteDummyTvShows(): List<TvShowResponse> {
        // using 20 data dummy tv show to equalize tv shows response' size from API
        val tvShows = ArrayList<TvShowResponse>()
        tvShows.add(TvShowResponse(
            85552,
            "Euphoria",
            "2019-06-16",
            "/jtnfNzqZwN4E32FGGxx1YZaBWWf.jpg",
            8.4f,))
        tvShows.add(TvShowResponse(
            2,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowResponse(
            3,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowResponse(
            4,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowResponse(
            5,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowResponse(
            6,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowResponse(
            7,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowResponse(
            8,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowResponse(
            9,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowResponse(
            10,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowResponse(
            11,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowResponse(
            12,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowResponse(
            13,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowResponse(
            14,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowResponse(
            15,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowResponse(
            16,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowResponse(
            17,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowResponse(
            18,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowResponse(
            19,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))
        tvShows.add(TvShowResponse(
            20,
            "Dummy title",
            "Dummy releaseDate",
            "Dummy posterUrl",
            0.0f))


        return tvShows
    }

    fun generateRemoteDummyTvShowDetail(tvShowId: Int): TvShowDetailResponse {
        val genreList = arrayListOf(
            GenreResponse(18, "Drama")
        )
        val runtimes = listOf(60)

        return TvShowDetailResponse(
            tvShowId,
            "Euphoria",
            "A group of high school students navigate love and friendships in a world of drugs, sex, trauma, and social media.",
            "2019-06-16",
            "2022-01-23",
            runtimes,
            8.4f,
            "/jtnfNzqZwN4E32FGGxx1YZaBWWf.jpg",
            "/oKt4J3TFjWirVwBqoHyIvv5IImd.jpg",
            14,
            2,
            genreList
        )
    }
}