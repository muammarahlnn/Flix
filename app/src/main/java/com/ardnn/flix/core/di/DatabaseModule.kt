package com.ardnn.flix.core.di

import android.content.Context
import androidx.room.Room
import com.ardnn.flix.core.data.source.local.room.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): FlixDatabase =
        Room.databaseBuilder(
            context,
            FlixDatabase::class.java,
            "Flix.db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideMovieDao(database: FlixDatabase): MovieDao =
        database.movieDao()

    @Provides
    fun provideTvShowDao(database: FlixDatabase): TvShowDao =
        database.tvShowDao()

    @Provides
    fun provideSectionDao(database: FlixDatabase): SectionDao =
        database.sectionDao()

    @Provides
    fun provideGenreDao(database: FlixDatabase): GenreDao =
        database.genreDao()
}