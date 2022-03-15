package com.ardnn.flix.moviedetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.flix.R
import com.ardnn.flix.core.data.Resource
import com.ardnn.flix.core.domain.model.Genre
import com.ardnn.flix.core.domain.model.Movie
import com.ardnn.flix.core.util.Helper
import com.ardnn.flix.core.util.SingleClickListener
import com.ardnn.flix.databinding.ActivityMovieDetailBinding
import com.ardnn.flix.genre.GenreActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity(), View.OnClickListener,
    SingleClickListener<Genre> {

    private val viewModel: MovieDetailViewModel by viewModels()

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var movie: Movie

    private var isSynopsisExtended = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get movie id and set it into view model
        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)
        viewModel.setMovieId(movieId)

        // set recyclerview genre
        binding.rvGenre.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        // subscribe view model
        subscribe()

        // click listener
        binding.btnBack.setOnClickListener(this)
        binding.btnFavorite.setOnClickListener(this)
        binding.clWrapperSynopsis.setOnClickListener(this)
    }

    private fun subscribe() {
        viewModel.movie.observe(this, { movieDetailResource ->
            if (movieDetailResource != null) {
                when (movieDetailResource) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        showAlert(false)

                        movieDetailResource.data?.let {
                            movie = it
                            setMovieDetailToWidgets()
                        }
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        showAlert(true)

                        Log.d(TAG, movieDetailResource.message.toString())
                        Toast.makeText(applicationContext, "An error occurred", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        viewModel.isSynopsisExtended.observe(this, { isExtended ->
            with (binding) {
                if (isExtended) {
                    tvSynopsis.maxLines = Int.MAX_VALUE
                    tvMore.text = getString(R.string.less)
                } else {
                    tvSynopsis.maxLines = 2
                    tvMore.text = getString(R.string.more)
                }
            }
        })

    }

    private fun setMovieDetailToWidgets() {
        with (binding) {
            // set images
            Helper.setImageGlide(
                this@MovieDetailActivity,
                Helper.getPosterTMDB(movie.posterUrl),
                ivPoster
            )
            Helper.setImageGlide(
                this@MovieDetailActivity,
                Helper.getWallpaperTMDB(movie.wallpaperUrl),
                ivWallpaper
            )

            // set btn favorite
            val isFavorite = movie.isFavorite
            binding.btnFavorite.setImageResource(
                if (isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_favorite_border_yellow
            )

            // set detail
            tvTitle.text = Helper.setTextString(movie.title)
            tvReleaseDate.text = Helper.setTextDate(movie.releaseDate)
            tvRuntime.text = Helper.setTextRuntime(this@MovieDetailActivity, movie.runtime)
            tvRating.text = Helper.setTextFloat(movie.rating)
            tvSynopsis.text = Helper.setTextString(movie.overview)

            // set recyclerview genre items
            val genreAdapter = GenreAdapter(movie.genres, this@MovieDetailActivity)
            rvGenre.adapter = genreAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.clAlert.visibility = View.VISIBLE
            binding.progressBar.visibility = View.VISIBLE
            binding.tvAlert.visibility = View.GONE
        } else {
            binding.clAlert.visibility = View.GONE
        }
    }

    private fun showAlert(isFailure: Boolean) {
        if (isFailure) {
            binding.clAlert.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            binding.tvAlert.visibility = View.VISIBLE
        } else {
            binding.clAlert.visibility = View.GONE
        }
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnBack -> {
                finish()
            }
            R.id.btnFavorite -> {
                viewModel.setIsFavorite()
                if (!movie.isFavorite) {
                    Toast.makeText(
                        this,
                        "${movie.title} has added to favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "${movie.title} has removed from favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            R.id.clWrapperSynopsis -> {
                isSynopsisExtended = !isSynopsisExtended
                viewModel.setIsSynopsisExtended(isSynopsisExtended)
            }
        }
    }

    override fun onItemClicked(item: Genre) {
        // to genre activity
        val toGenre = Intent(this, GenreActivity::class.java)
        toGenre.putExtra(GenreActivity.EXTRA_GENRE_ID, item.id)
        toGenre.putExtra(GenreActivity.EXTRA_GENRE_NAME, item.name)
        toGenre.putExtra(GenreActivity.EXTRA_TYPE, getString(R.string.movies))
        startActivity(toGenre)
    }

    companion object {
        private const val TAG = "MovieDetailActivity"
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }

}