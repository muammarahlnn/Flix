package com.ardnn.flix.ui.tvshow_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardnn.flix.R
import com.ardnn.flix.api.ImageSize
import com.ardnn.flix.api.response.GenreResponse
import com.ardnn.flix.api.response.TvShowDetailResponse
import com.ardnn.flix.databinding.ActivityTvShowDetailBinding
import com.ardnn.flix.ui.movie_detail.GenreAdapter
import com.ardnn.flix.utils.Helper

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

        // get tv show id and set it into view model
        val tvShowId = intent.getIntExtra(EXTRA_TV_SHOW_ID, 0)
        viewModel = ViewModelProvider(this, TvShowDetailViewModelFactory(tvShowId))[TvShowDetailViewModel::class.java]

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
        viewModel.tvShowDetail.observe(this, { tvShowDetail ->
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

        viewModel.isLoading.observe(this, { isLoading ->
            showLoading(isLoading)
        })
    }

    private fun setTvShowDetailToWidgets(tvShowDetail: TvShowDetailResponse) {
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
            tvTitle.text = tvShowDetail.title ?: "-"
            tvEpisodes.text = (tvShowDetail.numberOfEpisodes ?: "-").toString()
            tvSeasons.text = (tvShowDetail.numberOfSeasons ?: "-").toString()
            tvRuntime.text =
                if (!tvShowDetail.runtimes.isNullOrEmpty())
                    getString(R.string.minutes, tvShowDetail.runtimes[0])
                else "-"
            tvRating.text = (tvShowDetail.rating ?: "-").toString()
            tvFirstAiring.text =
                if (!tvShowDetail.firstAirDate.isNullOrEmpty())
                    Helper.convertToDate(tvShowDetail.firstAirDate)
                else "-"
            tvLastAiring.text =
                if (!tvShowDetail.lastAirDate.isNullOrEmpty())
                    Helper.convertToDate(tvShowDetail.lastAirDate)
                else "-"
            tvSynopsis.text = tvShowDetail.overview ?: "-"

            // set recyclerview genre items
            val genreAdapter = GenreAdapter(tvShowDetail.genreList as List<GenreResponse>)
            rvGenre.adapter = genreAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility =  if (isLoading) View.VISIBLE else View.GONE
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