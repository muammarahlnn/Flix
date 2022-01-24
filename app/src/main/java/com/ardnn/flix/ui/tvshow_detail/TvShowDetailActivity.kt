package com.ardnn.flix.ui.tvshow_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.flix.R
import com.ardnn.flix.data.source.local.entity.GenreEntity
import com.ardnn.flix.data.source.local.entity.TvShowDetailEntity
import com.ardnn.flix.data.source.remote.ImageSize
import com.ardnn.flix.data.source.remote.response.GenreResponse
import com.ardnn.flix.data.source.remote.response.TvShowDetailResponse
import com.ardnn.flix.databinding.ActivityTvShowDetailBinding
import com.ardnn.flix.ui.movie_detail.GenreAdapter
import com.ardnn.flix.utils.Helper
import com.ardnn.flix.viewmodel.ViewModelFactory

class TvShowDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var viewModel: TvShowDetailViewModel
    private lateinit var binding: ActivityTvShowDetailBinding

    private var isSynopsisExtended = false

    companion object {
        const val EXTRA_TV_SHOW_ID = "extra_tv_show_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvShowDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize view model
        val factory = ViewModelFactory.getInstance()
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
        binding.clWrapperSynopsis.setOnClickListener(this)
    }

    private fun subscribe() {
        binding.progressBar.visibility = View.VISIBLE
        viewModel.getTvShowDetail().observe(this, { tvShowDetail ->
            binding.progressBar.visibility = View.GONE
            setTvShowDetailToWidgets(tvShowDetail)
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

//        viewModel.isLoading.observe(this, { isLoading ->
//            showLoading(isLoading)
//        })
//
//        viewModel.isFailure.observe(this, { isFailure ->
//            showAlert(isFailure)
//        })
    }

    private fun setTvShowDetailToWidgets(tvShowDetail: TvShowDetailEntity) {
        with (binding) {
            // set poster and wallpaper (images)
            Helper.setImageGlide(
                this@TvShowDetailActivity,
                tvShowDetail.getPosterUrl(ImageSize.W342),
                ivPoster)

            Helper.setImageGlide(
                this@TvShowDetailActivity,
                tvShowDetail.getWallpaperUrl(ImageSize.W500),
                ivWallpaper)

            // set detail
            tvTitle.text = Helper.checkNullOrEmptyString(tvShowDetail.title)
            tvEpisodes.text = (tvShowDetail.numberOfEpisodes ?: "-").toString()
            tvSeasons.text = (tvShowDetail.numberOfSeasons ?: "-").toString()
            tvRuntime.text =
                if (!tvShowDetail.runtimes.isNullOrEmpty())
                    getString(R.string.minutes, tvShowDetail.runtimes[0])
                else "-"
            tvRating.text = (tvShowDetail.rating ?: "-").toString()
            tvFirstAiring.text = Helper.convertToDate(Helper.checkNullOrEmptyString(tvShowDetail.firstAirDate))
            tvLastAiring.text = Helper.convertToDate(Helper.checkNullOrEmptyString(tvShowDetail.lastAirDate))
            tvSynopsis.text = Helper.checkNullOrEmptyString(tvShowDetail.overview)

            // set recyclerview genre items
            val genreAdapter = GenreAdapter(tvShowDetail.genreList as List<GenreEntity>)
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
            R.id.clWrapperSynopsis -> {
                isSynopsisExtended = !isSynopsisExtended
                viewModel.setIsSynopsisExtended(isSynopsisExtended)
            }
        }
    }

}