package com.ardnn.flix.core.util

import com.ardnn.flix.core.data.source.local.entity.CastEntity
import com.ardnn.flix.core.data.source.local.entity.GenreEntity
import com.ardnn.flix.core.data.source.local.entity.MovieEntity
import com.ardnn.flix.core.data.source.local.entity.TvShowEntity
import com.ardnn.flix.core.data.source.local.entity.relation.GenreWithMovies
import com.ardnn.flix.core.data.source.local.entity.relation.GenreWithTvShows
import com.ardnn.flix.core.data.source.local.entity.relation.MovieWithGenres
import com.ardnn.flix.core.data.source.local.entity.relation.TvShowWithGenres
import com.ardnn.flix.core.data.source.remote.response.*
import com.ardnn.flix.core.domain.genre.model.Genre
import com.ardnn.flix.core.domain.genre.model.GenreMovies
import com.ardnn.flix.core.domain.genre.model.GenreTvShows
import com.ardnn.flix.core.domain.moviedetail.model.Cast
import com.ardnn.flix.core.domain.moviedetail.model.MovieDetail
import com.ardnn.flix.core.domain.movies.model.Movie
import com.ardnn.flix.core.domain.tvshowdetail.model.TvShowDetail
import com.ardnn.flix.core.domain.tvshows.model.TvShow

object DataMapper {

    fun mapMovieResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> =
        input.map {
            MovieEntity(
                id = it.id,
                title = it.title ?: "",
                releaseDate = it.releaseDate ?: "",
                posterUrl = it.posterUrl ?: "",
                rating = it.rating ?: 0f,
                popularity = it.popularity ?: 0f
            )
        }

    fun mapMovieDetailResponseToEntity(input: MovieDetailResponse): MovieEntity =
        MovieEntity(
            id = input.id,
            title = input.title ?: "",
            overview = input.overview ?: "",
            releaseDate = input.releaseDate ?: "",
            runtime = input.runtime ?: 0,
            rating = input.rating ?: 0f,
            popularity = input.popularity ?: 0f,
            posterUrl = input.posterUrl ?: "",
            wallpaperUrl = input.wallpaperUrl ?: "",
        )

    fun mapTvShowDetailResponseToEntity(input: TvShowDetailResponse): TvShowEntity =
        TvShowEntity(
            id = input.id,
            title = input.title ?: "",
            overview = input.overview ?: "",
            firstAirDate = input.firstAirDate ?: "",
            lastAirDate = input.lastAirDate ?: "",
            runtime =
                if (input.runtimes.isNullOrEmpty()) 0
                else input.runtimes[0],
            rating = input.rating ?: 0f,
            popularity = input.popularity ?: 0f,
            posterUrl = input.posterUrl ?: "",
            wallpaperUrl = input.wallpaperUrl ?: "",
            numberOfEpisodes = input.numberOfEpisodes ?: 0,
            numberOfSeasons = input.numberOfSeasons ?: 0,
        )

    fun mapTvShowResponsesToEntities(input: List<TvShowResponse>): List<TvShowEntity> =
        input.map {
            TvShowEntity(
                id = it.id,
                title = it.title ?: "",
                firstAirDate = it.firstAirDate ?: "",
                posterUrl = it.posterUrl ?: "",
                rating = it.rating ?: 0f,
                popularity = it.popularity ?: 0f
            )
        }

    fun mapGenreResponsesToEntities(input: List<GenreResponse>): List<GenreEntity> =
        input.map {
            GenreEntity(
                id = it.id,
                name = it.name ?: ""
            )
        }

    fun mapCastResponsesToEntities(input: List<CastResponse>, filmId: Int): List<CastEntity> =
        input.map {
            CastEntity(
                id = it.id,
                filmId = filmId,
                name = it.name ?: "",
                character = it.character ?: "",
                profileUrl = it.profileUrl ?: "",
            )
        }


    fun mapMovieEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                releaseDate = it.releaseDate,
                rating = it.rating,
                posterUrl = it.posterUrl,
            )
        }

    fun mapMovieEntitiesToMovieDetailDomain(input: List<MovieEntity>): List<MovieDetail> =
        input.map {
            MovieDetail(
                id = it.id,
                title = it.title,
                overview = it.overview,
                releaseDate = it.releaseDate,
                runtime = it.runtime,
                rating = it.rating,
                popularity = it.popularity,
                posterUrl = it.posterUrl,
                wallpaperUrl = it.wallpaperUrl,
                isFavorite = it.isFavorite,
                isDetailFetched = it.isDetailFetched,
            )
        }

    fun mapTvShowEntitiesToDomain(input: List<TvShowEntity>): List<TvShow> =
        input.map {
            TvShow(
                id = it.id,
                title = it.title,
                firstAirDate = it.firstAirDate,
                rating = it.rating,
                posterUrl = it.posterUrl,
            )
        }

    fun mapTvShowEntitiesToTvShowDetailDomain(input: List<TvShowEntity>): List<TvShowDetail> =
        input.map {
            TvShowDetail(
                id = it.id,
                title = it.title,
                overview = it.overview,
                firstAirDate = it.firstAirDate,
                lastAirDate = it.lastAirDate,
                runtime = it.runtime,
                rating = it.rating,
                popularity = it.popularity,
                posterUrl = it.posterUrl,
                wallpaperUrl = it.wallpaperUrl,
                numberOfEpisodes = it.numberOfEpisodes,
                numberOfSeasons = it.numberOfSeasons,
                isFavorite = it.isFavorite,
                isDetailFetched = it.isDetailFetched,
            )
        }

    fun mapMovieWithGenresEntityToDomain(input: MovieWithGenres): MovieDetail {
        val movieEntity = input.movie
        val genres = mapGenreEntitiesToDomain(input.genres)
        return MovieDetail(
            id = movieEntity.id,
            title = movieEntity.title,
            overview = movieEntity.overview,
            releaseDate = movieEntity.releaseDate,
            runtime = movieEntity.runtime,
            rating = movieEntity.rating,
            popularity = movieEntity.popularity,
            posterUrl = movieEntity.posterUrl,
            wallpaperUrl = movieEntity.wallpaperUrl,
            genres = genres,
            isFavorite = movieEntity.isFavorite,
            isDetailFetched = movieEntity.isDetailFetched,
        )
    }

    fun mapTvShowWithGenresEntityToDomain(input: TvShowWithGenres): TvShowDetail {
        val tvShowEntity = input.tvShow
        val genres = mapGenreEntitiesToDomain(input.genres)
        return TvShowDetail(
            id = tvShowEntity.id,
            title = tvShowEntity.title,
            overview = tvShowEntity.overview,
            firstAirDate = tvShowEntity.firstAirDate,
            lastAirDate = tvShowEntity.lastAirDate,
            runtime = tvShowEntity.runtime,
            rating = tvShowEntity.rating,
            popularity = tvShowEntity.popularity,
            posterUrl = tvShowEntity.posterUrl,
            wallpaperUrl = tvShowEntity.wallpaperUrl,
            numberOfEpisodes = tvShowEntity.numberOfEpisodes,
            numberOfSeasons = tvShowEntity.numberOfSeasons,
            genres = genres,
            isFavorite = tvShowEntity.isFavorite,
            isDetailFetched = tvShowEntity.isDetailFetched,
        )
    }

    fun mapCastEntitiesToDomain(input: List<CastEntity>): List<Cast> =
        input.map {
            Cast(
                id = it.id,
                filmId = it.filmId,
                name = it.name,
                character = it.character,
                profileUrl = it.profileUrl,
            )
        }

    private fun mapGenreEntitiesToDomain(input: List<GenreEntity>): List<Genre> =
        input.map {
            Genre(
                id = it.id,
                name = it.name
            )
        }

    fun mapGenreWithMoviesEntityToDomain(input: GenreWithMovies): GenreMovies {
        val genreEntity = input.genre
        val movies = mapMovieEntitiesToDomain(input.movies)
        return GenreMovies(
            id = genreEntity.id,
            name = genreEntity.name,
            movies = movies
        )
    }

    fun mapGenreWithTvShowsEntityToDomain(input: GenreWithTvShows): GenreTvShows {
        val genreEntity = input.genre
        val tvShows = mapTvShowEntitiesToDomain(input.tvShows)
        return GenreTvShows(
            id = genreEntity.id,
            name = genreEntity.name,
            tvShows = tvShows
        )
    }

    fun mapMovieDetailDomainToEntity(input: MovieDetail): MovieEntity =
        MovieEntity(
            id = input.id,
            title = input.title,
            overview = input.overview,
            releaseDate = input.releaseDate,
            runtime = input.runtime,
            rating = input.rating,
            popularity = input.popularity,
            posterUrl = input.posterUrl,
            wallpaperUrl = input.wallpaperUrl,
            isFavorite = input.isFavorite,
            isDetailFetched = input.isDetailFetched
        )

    fun mapTvShowDetailDomainToEntity(input: TvShowDetail): TvShowEntity =
        TvShowEntity(
            id = input.id,
            title = input.title,
            overview = input.overview,
            firstAirDate = input.firstAirDate,
            lastAirDate = input.lastAirDate,
            runtime = input.runtime,
            rating = input.rating,
            popularity = input.popularity,
            posterUrl = input.posterUrl,
            wallpaperUrl = input.wallpaperUrl,
            numberOfEpisodes = input.numberOfEpisodes,
            numberOfSeasons = input.numberOfSeasons,
            isFavorite = input.isFavorite,
            isDetailFetched = input.isDetailFetched
        )
}