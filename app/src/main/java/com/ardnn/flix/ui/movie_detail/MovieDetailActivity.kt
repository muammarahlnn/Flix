package com.ardnn.flix.ui.movie_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.flix.R
import com.ardnn.flix.data.source.local.entity.GenreEntity
import com.ardnn.flix.data.source.local.entity.MovieDetailEntity
import com.ardnn.flix.data.source.remote.ImageSize
import com.ardnn.flix.data.source.remote.response.GenreResponse
import com.ardnn.flix.data.source.remote.response.MovieDetailResponse
import com.ardnn.flix.databinding.ActivityMovieDetailBinding
import com.ardnn.flix.utils.Helper
import com.ardnn.flix.viewmodel.ViewModelFactory

class MovieDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var binding: ActivityMovieDetailBinding

    private var isSynopsisExtended = false

    companion object {
        const val EXTRA_MOVIE_ID = "extra_movie_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize view model
        val factory = ViewModelFactory.getInstance()
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
        binding.clWrapperSynopsis.setOnClickListener(this)
    }

    private fun subscribe() {
        viewModel.getMovieDetail().observe(this, { movieDetail ->
            setMovieDetailToWidgets(movieDetail)
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

        viewModel.getIsLoading().observe(this, { isLoading ->
            showLoading(isLoading)
        })

        viewModel.getIsLoadFailure().observe(this, { isFailure ->
            showAlert(isFailure)
        })
    }

    private fun setMovieDetailToWidgets(movieDetail: MovieDetailEntity) {
        with (binding) {
            // set poster and wallpaper (images)
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

    private fun showAlert(isFailure: Boolean) {
        binding.clAlert.visibility = if (isFailure) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnBack -> {
                finish()
            }
            R.id.clWrapperSynopsis -> {
                isSynopsisExtended = !isSynopsisExtended
                viewModel.setIsSynopsisExtended(isSynopsisExtended)
            }
        }
    }
}