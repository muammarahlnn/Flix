package com.ardnn.flix.core.util

import com.ardnn.flix.core.data.source.local.entity.GenreEntity
import com.ardnn.flix.core.data.source.local.entity.MovieEntity
import com.ardnn.flix.core.data.source.local.entity.TvShowEntity
import com.ardnn.flix.core.data.source.local.entity.relation.GenreWithMovies
import com.ardnn.flix.core.data.source.local.entity.relation.GenreWithTvShows
import com.ardnn.flix.core.data.source.local.entity.relation.MovieWithGenres
import com.ardnn.flix.core.data.source.local.entity.relation.TvShowWithGenres
import com.ardnn.flix.core.data.source.local.ImageSizeTMDB
import com.ardnn.flix.core.data.source.remote.response.*
import com.ardnn.flix.core.domain.model.Genre
import com.ardnn.flix.core.domain.model.Movie
import com.ardnn.flix.core.domain.model.TvShow

object DataMapper {

    fun mapMovieResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        return input.map {
            MovieEntity(
                id = it.id,
                title = it.title ?: "",
                releaseDate = it.releaseDate ?: "",
                posterUrl = it.posterUrl ?: "",
                rating = it.rating ?: 0f,
                popularity = it.popularity ?: 0f
            )
        }
    }

    fun mapMovieDetailResponseToEntity(input: MovieDetailResponse): MovieEntity {
        return MovieEntity(
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
    }

    fun mapTvShowDetailResponseToEntity(input: TvShowDetailResponse): TvShowEntity {
        return TvShowEntity(
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
    }

    fun mapTvShowResponsesToEntities(input: List<TvShowResponse>): List<TvShowEntity> {
        return input.map {
            TvShowEntity(
                id = it.id,
                title = it.title ?: "",
                firstAirDate = it.firstAirDate ?: "",
                posterUrl = it.posterUrl ?: "",
                rating = it.rating ?: 0f,
                popularity = it.popularity ?: 0f
            )
        }
    }

    fun mapGenreResponsesToEntities(input: List<GenreResponse>): List<GenreEntity> {
        return input.map {
            GenreEntity(
                id = it.id,
                name = it.name ?: ""
            )
        }
    }

    fun mapMovieEntitiesToDomain(input: List<MovieEntity>): List<Movie> {
        return input.map {
            Movie(
                id = it.id,
                title = it.title,
                overview = it.overview,
                releaseDate = it.releaseDate,
                runtime = it.runtime,
                rating = it.rating,
                popularity = it.popularity,
                posterUrl = it.getPosterUrl(ImageSizeTMDB.W500),
                wallpaperUrl = it.getWallpaperUrl(ImageSizeTMDB.W780),
                isFavorite = it.isFavorite,
                isDetailFetched = it.isDetailFetched
            )
        }
    }

    fun mapTvShowEntitiesToDomain(input: List<TvShowEntity>): List<TvShow> {
        return input.map {
            TvShow(
                id = it.id,
                title = it.title,
                overview = it.overview,
                firstAirDate = it.firstAirDate,
                lastAirDate = it.lastAirDate,
                runtime = it.runtime,
                rating = it.rating,
                popularity = it.popularity,
                posterUrl = it.getPosterUrl(ImageSizeTMDB.W500),
                wallpaperUrl = it.getWallpaperUrl(ImageSizeTMDB.W780),
                numberOfEpisodes = it.numberOfEpisodes,
                numberOfSeasons = it.numberOfSeasons,
                isFavorite = it.isFavorite,
                isDetailFetched = it.isDetailFetched
            )
        }
    }

    fun mapMovieWithGenresEntityToDomain(input: MovieWithGenres): Movie {
        val movieEntity = input.movie
        val genres = mapGenreEntitiesToDomain(input.genres)
        return Movie(
            id = movieEntity.id,
            title = movieEntity.title,
            overview = movieEntity.overview,
            releaseDate = movieEntity.releaseDate,
            runtime = movieEntity.runtime,
            rating = movieEntity.rating,
            popularity = movieEntity.popularity,
            posterUrl = movieEntity.getPosterUrl(ImageSizeTMDB.W500),
            wallpaperUrl = movieEntity.getWallpaperUrl(ImageSizeTMDB.W780),
            genres = genres,
            isFavorite = movieEntity.isFavorite,
            isDetailFetched = movieEntity.isDetailFetched
        )
    }

    fun mapTvShowWithGenresEntityToDomain(input: TvShowWithGenres): TvShow {
        val tvShowEntity = input.tvShow
        val genres = mapGenreEntitiesToDomain(input.genres)
        return TvShow(
            id = tvShowEntity.id,
            title = tvShowEntity.title,
            overview = tvShowEntity.overview,
            firstAirDate = tvShowEntity.firstAirDate,
            lastAirDate = tvShowEntity.lastAirDate,
            runtime = tvShowEntity.runtime,
            rating = tvShowEntity.rating,
            popularity = tvShowEntity.popularity,
            posterUrl = tvShowEntity.getPosterUrl(ImageSizeTMDB.W500),
            wallpaperUrl = tvShowEntity.getWallpaperUrl(ImageSizeTMDB.W780),
            numberOfEpisodes = tvShowEntity.numberOfEpisodes,
            numberOfSeasons = tvShowEntity.numberOfSeasons,
            genres = genres,
            isFavorite = tvShowEntity.isFavorite,
            isDetailFetched = tvShowEntity.isDetailFetched
        )
    }

    fun mapGenreEntitiesToDomain(input: List<GenreEntity>): List<Genre> {
        return input.map {
            Genre(
                id = it.id,
                name = it.name
            )
        }
    }

    fun mapGenreWithMoviesEntityToDomain(input: GenreWithMovies): Genre {
        val genreEntity = input.genre
        val movies = mapMovieEntitiesToDomain(input.movies)
        return Genre(
            id = genreEntity.id,
            name = genreEntity.name,
            movies = movies
        )
    }

    fun mapGenreWithTvShowsEntityToDomain(input: GenreWithTvShows): Genre {
        val genreEntity = input.genre
        val tvShows = mapTvShowEntitiesToDomain(input.tvShows)
        return Genre(
            id = genreEntity.id,
            name = genreEntity.name,
            tvShows = tvShows
        )
    }

    fun mapMovieDomainToEntity(input: Movie): MovieEntity {
        val movieEntity = MovieEntity(
            id = input.id,
            title = input.title,
            overview = input.overview,
            releaseDate = input.releaseDate,
            runtime = input.runtime,
            rating = input.rating,
            popularity = input.popularity,
            posterUrl = input.posterUrl,
            wallpaperUrl = input.wallpaperUrl
        )
        movieEntity.isFavorite = input.isFavorite
        movieEntity.isDetailFetched = input.isDetailFetched

        return movieEntity
    }

    fun mapTvShowDomainToEntity(input: TvShow): TvShowEntity {
        val tvShowEntity = TvShowEntity(
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
        )
        tvShowEntity.isFavorite = input.isFavorite
        tvShowEntity.isDetailFetched = input.isDetailFetched

        return tvShowEntity
    }
}