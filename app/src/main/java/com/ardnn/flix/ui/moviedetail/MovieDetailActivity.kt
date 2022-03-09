package com.ardnn.flix.ui.moviedetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.flix.R
import com.ardnn.flix.data.source.local.entity.GenreEntity
import com.ardnn.flix.data.source.local.entity.relation.MovieWithGenres
import com.ardnn.flix.data.source.remote.ImageSize
import com.ardnn.flix.databinding.ActivityMovieDetailBinding
import com.ardnn.flix.ui.genre.GenreActivity
import com.ardnn.flix.util.Helper
import com.ardnn.flix.util.SingleClickListener
import com.ardnn.flix.viewmodel.ViewModelFactory
import com.ardnn.flix.vo.Status

class MovieDetailActivity : AppCompatActivity(), View.OnClickListener,
    SingleClickListener<GenreEntity> {

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var movieWithGenres: MovieWithGenres

    private var isSynopsisExtended = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize view model
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[MovieDetailViewModel::class.java]

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
                when (movieDetailResource.status) {
                    Status.LOADING -> {
                        showLoading(true)
                    }
                    Status.SUCCESS -> {
                        if (movieDetailResource.data != null) {
                            showLoading(false)
                            showAlert(false)

                            movieWithGenres = movieDetailResource.data
                            setMovieDetailToWidgets()
                        }
                    }
                    Status.ERROR -> {
                        showLoading(false)
                        showAlert(true)

                        Log.d(TAG, movieDetailResource.message.toString())
                        Toast.makeText(applicationContext, "An error occurred", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        viewModel.movieCredits.observe(this, { movieCredits ->
            val credits = movieCredits.body
            for (credit in credits) {
                Log.d(TAG, credit.name.toString())
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
            val movie = movieWithGenres.movie
            // set images
            Helper.setImageGlide(
                this@MovieDetailActivity,
                movie.getPosterUrl(ImageSize.W342),
                ivPoster)
            ivPoster.tag = movie.posterUrl

            Helper.setImageGlide(
                this@MovieDetailActivity,
                movie.getWallpaperUrl(ImageSize.W500),
                ivWallpaper)
            ivWallpaper.tag = movie.wallpaperUrl

            val isFavorite = movie.isFavorite
            binding.btnFavorite.setImageResource(
                if (isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_favorite_border_yellow
            )

            // set detail
            tvTitle.text = Helper.checkNullOrEmptyString(movie.title)
            tvReleaseDate.text = Helper.convertToDate(Helper.checkNullOrEmptyString(movie.releaseDate))
            tvRuntime.text =
                if (movie.runtime != null)
                    getString(R.string.minutes, movie.runtime)
                else "-"
            tvRating.text = (movie.rating ?: "-").toString()
            tvSynopsis.text = Helper.checkNullOrEmptyString(movie.overview)

            // set recyclerview genre items
            val genreAdapter = GenreAdapter(movieWithGenres.genres, this@MovieDetailActivity)
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
                val movie = movieWithGenres.movie
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

    override fun onItemClicked(item: GenreEntity) {
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