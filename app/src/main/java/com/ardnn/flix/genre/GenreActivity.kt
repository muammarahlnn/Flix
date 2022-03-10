package com.ardnn.flix.genre

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ardnn.flix.R
import com.ardnn.flix.databinding.ActivityGenreBinding
import com.ardnn.flix.core.util.PagedListDataSources
import com.ardnn.flix.core.viewmodel.ViewModelFactory

class GenreActivity : AppCompatActivity() {

    private lateinit var viewModel: GenreViewModel
    private lateinit var binding: ActivityGenreBinding

    private var genreType = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set action bar
        setSupportActionBar(binding.toolbar.root)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.ivIcon.visibility = View.GONE

        // set action bar title
        val genreName = intent.getStringExtra(EXTRA_GENRE_NAME)
        genreType = intent.getStringExtra(EXTRA_TYPE).toString()
        binding.toolbar.tvSection.text = getString(R.string.genre_toolbar_title, genreName, genreType)

        // initialize view model
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[GenreViewModel::class.java]

        // get id and set it on view model
        val genreId = intent.getIntExtra(EXTRA_GENRE_ID, 0)
        viewModel.setGenreId(genreId)

        // subscribe view model
        subscribe()

        // click listener

    }

    private fun subscribe() {
        when (genreType) {
            getString(R.string.movies) -> {
                viewModel.getGenreWithMovies().observe(this, { genreWithMovies ->
                    val movies = genreWithMovies.movies
                    val pagedMovies = PagedListDataSources.snapshot(movies)
                    val adapter = GenreMoviesAdapter()
                    adapter.submitList(pagedMovies)
                    binding.recyclerView.adapter = adapter
                })
            }
            getString(R.string.tv_shows) -> {
                viewModel.getGenreWithTvShows().observe(this, { genreWithTvShows ->
                    val tvShows = genreWithTvShows.tvShows
                    val pagedTvShows = PagedListDataSources.snapshot(tvShows)
                    val adapter = GenreTvShowsAdapter()
                    adapter.submitList(pagedTvShows)
                    binding.recyclerView.adapter = adapter
                })
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val EXTRA_GENRE_ID = "extra_genre_id"
        const val EXTRA_GENRE_NAME = "extra_genre_name"
        const val EXTRA_TYPE = "extra_type"
    }
}