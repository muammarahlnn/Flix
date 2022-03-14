package com.ardnn.flix.genre

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ardnn.flix.R
import com.ardnn.flix.core.util.PagedListDataSources
import com.ardnn.flix.databinding.ActivityGenreBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreActivity : AppCompatActivity() {

    private val viewModel: GenreViewModel by viewModels()

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
                viewModel.getGenreWithMovies().observe(this, { genre ->
                    val movies = genre.movies
                    val pagedMovies = PagedListDataSources.snapshot(movies)
                    val adapter = GenreMoviesAdapter()
                    adapter.submitList(pagedMovies)
                    binding.recyclerView.adapter = adapter
                })
            }
            getString(R.string.tv_shows) -> {
                viewModel.getGenreWithTvShows().observe(this, { genre ->
                    val tvShows = genre.tvShows
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