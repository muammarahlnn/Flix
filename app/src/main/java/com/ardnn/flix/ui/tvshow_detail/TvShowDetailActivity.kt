package com.ardnn.flix.ui.tvshow_detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.flix.R
import com.ardnn.flix.data.source.local.entity.relation.TvShowDetailWithGenres
import com.ardnn.flix.data.source.remote.ImageSize
import com.ardnn.flix.databinding.ActivityTvShowDetailBinding
import com.ardnn.flix.ui.movie_detail.GenreAdapter
import com.ardnn.flix.utils.Helper
import com.ardnn.flix.viewmodel.ViewModelFactory
import com.ardnn.flix.vo.Status

class TvShowDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: TvShowDetailViewModel
    private lateinit var binding: ActivityTvShowDetailBinding
    private lateinit var tvShowDetailWithGenres: TvShowDetailWithGenres

    private var isSynopsisExtended = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvShowDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize view model
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[TvShowDetailViewModel::class.java]

        // get tv show id and set it into view model
        val tvShowId = intent.getIntExtra(EXTRA_TV_SHOW_ID, 0)
        viewModel.setTvShowId(tvShowId)

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
        viewModel.tvShowDetail.observe(this, { tvShowDetailResource ->
            if (tvShowDetailResource != null) {
                when (tvShowDetailResource.status) {
                    Status.LOADING -> {
                        showLoading(true)
                    }
                    Status.SUCCESS -> {
                        if (tvShowDetailResource.data != null) {
                            showLoading(false)
                            tvShowDetailWithGenres = tvShowDetailResource.data
                            setTvShowDetailToWidgets()
                        }
                    }
                    Status.ERROR -> {
                        showLoading(false)
                        Toast.makeText(applicationContext, "An error occurred", Toast.LENGTH_SHORT)
                            .show()
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

    private fun setTvShowDetailToWidgets() {
        with (binding) {
            val tvShowDetail = tvShowDetailWithGenres.tvShowDetail

            // set images
            Helper.setImageGlide(
                this@TvShowDetailActivity,
                tvShowDetail.getPosterUrl(ImageSize.W342),
                ivPoster)
            ivPoster.tag = tvShowDetail.posterUrl

            Helper.setImageGlide(
                this@TvShowDetailActivity,
                tvShowDetail.getWallpaperUrl(ImageSize.W500),
                ivWallpaper)
            ivWallpaper.tag = tvShowDetail.wallpaperUrl

            val isFavorite = tvShowDetail.isFavorite
            binding.btnFavorite.setImageResource(
                if (isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_favorite_border_yellow
            )

            // set detail
            tvTitle.text = Helper.checkNullOrEmptyString(tvShowDetail.title)
            tvEpisodes.text = (tvShowDetail.numberOfEpisodes ?: "-").toString()
            tvSeasons.text = (tvShowDetail.numberOfSeasons ?: "-").toString()
            tvRuntime.text =
                if (tvShowDetail.runtime != null)
                    getString(R.string.minutes, tvShowDetail.runtime)
                else "-"
            tvRating.text = (tvShowDetail.rating ?: "-").toString()
            tvFirstAiring.text = Helper.convertToDate(Helper.checkNullOrEmptyString(tvShowDetail.firstAirDate))
            tvLastAiring.text = Helper.convertToDate(Helper.checkNullOrEmptyString(tvShowDetail.lastAirDate))
            tvSynopsis.text = Helper.checkNullOrEmptyString(tvShowDetail.overview)

            // set recyclerview genre items
            val genreAdapter = GenreAdapter(tvShowDetailWithGenres.genres)
            rvGenre.adapter = genreAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =  if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showAlert(isFailure: Boolean) {
        binding.clAlert.visibility = if (isFailure) View.VISIBLE else View.GONE
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnBack -> {
                finish()
            }
            R.id.btnFavorite -> {
                viewModel.setIsFavorite()
                val tvShowDetail = tvShowDetailWithGenres.tvShowDetail
                if (!tvShowDetail.isFavorite) {
                    Toast.makeText(
                        this,
                        "${tvShowDetail.title} has added to favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "${tvShowDetail.title} has removed from favorites",
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

    companion object {
        const val EXTRA_TV_SHOW_ID = "extra_tv_show_id"
    }
}