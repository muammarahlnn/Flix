package com.ardnn.flix

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.ardnn.flix.utils.FilmsData

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // debug
        val filmsData = FilmsData(this)

        val movies = filmsData.movies
        for (movie in movies) {
            println(movie.title)
            println(movie.releaseDate)
            println(movie.rating)
            println(movie.runtime)
            println(movie.poster)
            println("")
        }

        val tvShows = filmsData.tvShows
        for (tvShow in tvShows) {
            println(tvShow.title)
            println(tvShow.releaseDate)
            println(tvShow.rating)
            println(tvShow.runtime)
            println(tvShow.poster)
            println("")
        }

        val imageView = findViewById<ImageView>(R.id.ivTest)
        imageView.setImageResource(movies[5].poster)

    }
}