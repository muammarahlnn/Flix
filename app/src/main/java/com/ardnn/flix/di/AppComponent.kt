package com.ardnn.flix.di

import com.ardnn.flix.core.di.CoreComponent
import com.ardnn.flix.favorites.FavoritesFragment
import com.ardnn.flix.genre.GenreActivity
import com.ardnn.flix.moviedetail.MovieDetailActivity
import com.ardnn.flix.movies.MoviesFragment
import com.ardnn.flix.tvshowdetail.TvShowDetailActivity
import com.ardnn.flix.tvshows.TvShowsFragment
import dagger.Component

@AppScope
@Component(
    dependencies = [CoreComponent::class],
    modules = [AppModule::class, ViewModelModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): AppComponent
    }

    fun inject(fragment: MoviesFragment)
    fun inject(fragment: TvShowsFragment)
    fun inject(fragment: FavoritesFragment)
    fun inject(activity: MovieDetailActivity)
    fun inject(activity: TvShowDetailActivity)
    fun inject(activity: GenreActivity)

}