package com.ardnn.flix.ui.movie_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.flix.R
import com.ardnn.flix.api.ImageSize
import com.ardnn.flix.api.response.GenreResponse
import com.ardnn.flix.api.response.MovieDetailResponse
import com.ardnn.flix.databinding.ActivityMovieDetailBinding
import com.ardnn.flix.utils.Helper

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

        // get movie id and set it into view model
        val movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)
        viewModel = ViewModelProvider(this, MovieDetailViewModelFactory(movieId))[MovieDetailViewModel::class.java]

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
        viewModel.movieDetail.observe(this, { movieDetail ->
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
    }

    private fun setMovieDetailToWidgets(movieDetail: MovieDetailResponse) {
        with (binding) {
            // set poster and wallpaper (images)
            Helper.setImageGlide(
                this@MovieDetailActivity,
                movieDetail.getPosterUrl(ImageSize.W342),
                ivPoster)

            Helper.setImageGlide(
                this@MovieDetailActivity,
                movieDetail.getWallpaperUrl(ImageSize.W500),
                ivWallpaper)

            // set detail
            tvTitle.text = movieDetail.title ?: "-"
            tvReleaseDate.text =
                if (!movieDetail.releaseDate.isNullOrEmpty())
                    Helper.convertToDate(movieDetail.releaseDate)
                else "-"
            tvRuntime.text =
                if (movieDetail.runtime != null)
                    getString(R.string.minutes, movieDetail.runtime)
                else "-"
            tvRating.text = (movieDetail.rating ?: "-").toString()
            tvSynopsis.text = movieDetail.overview ?: "-"

            // set recyclerview genre items
            val genreAdapter = GenreAdapter(movieDetail.genreList as List<GenreResponse>)
            rvGenre.adapter = genreAdapter
        }
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