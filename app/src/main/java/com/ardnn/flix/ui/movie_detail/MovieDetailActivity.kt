package com.ardnn.flix.ui.movie_detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.flix.R
import com.ardnn.flix.data.source.local.entity.GenreEntity
import com.ardnn.flix.data.source.local.entity.MovieDetailEntity
import com.ardnn.flix.data.source.remote.ImageSize
import com.ardnn.flix.databinding.ActivityMovieDetailBinding
import com.ardnn.flix.utils.Helper
import com.ardnn.flix.viewmodel.ViewModelFactory
import com.ardnn.flix.vo.Status

class MovieDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var movieDetail: MovieDetailEntity

    private var isSynopsisExtended = false
    private var isFavorite = false

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }

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
        viewModel.movieDetail.observe(this, { movieDetailResource ->
            if (movieDetailResource != null) {
                when (movieDetailResource.status) {
                    Status.LOADING -> {
                        showLoading(true)
                    }
                    Status.SUCCESS -> {
                        if (movieDetailResource.data != null) {
                            showLoading(false)
                            movieDetail = movieDetailResource.data
                            isFavorite = movieDetail.isFavorite
                            setMovieDetailToWidgets()
                        }
                    }
                    Status.ERROR -> {
                        showLoading(false)
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
                movieDetail.getPosterUrl(ImageSize.W342),
                ivPoster)
            ivPoster.tag = movieDetail.posterUrl

            Helper.setImageGlide(
                this@MovieDetailActivity,
                movieDetail.getWallpaperUrl(ImageSize.W500),
                ivWallpaper)
            ivWallpaper.tag = movieDetail.wallpaperUrl

            val isFavorite = movieDetail.isFavorite
            binding.btnFavorite.setImageResource(
                if (isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_favorite_border_yellow
            )

            // set detail
            tvTitle.text = Helper.checkNullOrEmptyString(movieDetail.title)
            tvReleaseDate.text = Helper.convertToDate(Helper.checkNullOrEmptyString(movieDetail.releaseDate))
            tvRuntime.text =
                if (movieDetail.runtime != null)
                    getString(R.string.minutes, movieDetail.runtime)
                else "-"
            tvRating.text = (movieDetail.rating ?: "-").toString()
            tvSynopsis.text = Helper.checkNullOrEmptyString(movieDetail.overview)

            // set recyclerview genre items
            val genreAdapter = GenreAdapter(movieDetail.genreList as List<GenreEntity>)
            rvGenre.adapter = genreAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnBack -> {
                finish()
            }
            R.id.btnFavorite -> {
                viewModel.setIsFavorite()
                if (!movieDetail.isFavorite) {
                    Toast.makeText(
                        this,
                        "${movieDetail.title} has added to favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "${movieDetail.title} has removed from favorites",
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
}