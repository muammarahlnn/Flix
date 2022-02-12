package com.ardnn.flix.ui.tvshow_detail

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
import com.ardnn.flix.data.source.local.entity.relation.TvShowDetailWithGenres
import com.ardnn.flix.data.source.remote.ImageSize
import com.ardnn.flix.databinding.ActivityTvShowDetailBinding
import com.ardnn.flix.ui.genre.GenreActivity
import com.ardnn.flix.ui.movie_detail.GenreAdapter
import com.ardnn.flix.utils.Helper
import com.ardnn.flix.utils.SingleClickListener
import com.ardnn.flix.viewmodel.ViewModelFactory
import com.ardnn.flix.vo.Status

class TvShowDetailActivity : AppCompatActivity(), View.OnClickListener,
    SingleClickListener<GenreEntity> {

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
                            showAlert(false)

                            tvShowDetailWithGenres = tvShowDetailResource.data
                            setTvShowDetailToWidgets()
                        }
                    }
                    Status.ERROR -> {
                        showLoading(false)
                        showAlert(true)

                        Log.d(TAG, tvShowDetailResource.message.toString())
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
            val genreAdapter = GenreAdapter(tvShowDetailWithGenres.genres, this@TvShowDetailActivity)
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

    override fun onItemClicked(item: GenreEntity) {
        // to genre activity
        val toGenre = Intent(this, GenreActivity::class.java)
        toGenre.putExtra(GenreActivity.EXTRA_GENRE_ID, item.id)
        toGenre.putExtra(GenreActivity.EXTRA_GENRE_NAME, item.name)
        toGenre.putExtra(GenreActivity.EXTRA_TYPE, getString(R.string.tv_shows))
        startActivity(toGenre)
    }

    companion object {
        private const val TAG = "TvShowDetailActivity"
        const val EXTRA_TV_SHOW_ID = "extra_tv_show_id"
    }

}