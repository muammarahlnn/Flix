package com.ardnn.flix.tvshowdetail

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
import com.ardnn.flix.core.domain.model.TvShow
import com.ardnn.flix.core.util.Helper
import com.ardnn.flix.core.util.SingleClickListener
import com.ardnn.flix.databinding.ActivityTvShowDetailBinding
import com.ardnn.flix.genre.GenreActivity
import com.ardnn.flix.moviedetail.GenreAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowDetailActivity : AppCompatActivity(), View.OnClickListener,
    SingleClickListener<Genre> {

    private val viewModel: TvShowDetailViewModel by viewModels()

    private lateinit var binding: ActivityTvShowDetailBinding
    private lateinit var tvShow: TvShow

    private var isSynopsisExtended = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvShowDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        viewModel.tvShow.observe(this, { tvShowResource ->
            if (tvShowResource != null) {
                when (tvShowResource) {
                    is Resource.Loading -> {
                        showLoading(true)
                    }
                    is Resource.Success -> {
                        showLoading(false)
                        showAlert(false)

                        tvShowResource.data?.let {
                            tvShow = it
                            setTvShowDetailToWidgets()
                        }
                    }
                    is Resource.Error -> {
                        showLoading(false)
                        showAlert(true)

                        Log.d(TAG, tvShowResource.message.toString())
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
            // set images
            Helper.setImageGlide(
                this@TvShowDetailActivity,
                Helper.getPosterTMDB(tvShow.posterUrl),
                ivPoster
            )
            Helper.setImageGlide(
                this@TvShowDetailActivity,
                Helper.getWallpaperTMDB(tvShow.wallpaperUrl),
                ivWallpaper
            )

            // set btn favorite
            val isFavorite = tvShow.isFavorite
            binding.btnFavorite.setImageResource(
                if (isFavorite) R.drawable.ic_favorite
                else R.drawable.ic_favorite_border_yellow
            )

            // set detail
            tvTitle.text = Helper.setTextString(tvShow.title)
            tvEpisodes.text = Helper.setTextNum(tvShow.numberOfEpisodes)
            tvSeasons.text = Helper.setTextNum(tvShow.numberOfSeasons)
            tvRuntime.text = Helper.setTextRuntime(this@TvShowDetailActivity, tvShow.runtime)
            tvRating.text = Helper.setTextFloat(tvShow.rating)
            tvFirstAiring.text = Helper.setTextDate(tvShow.firstAirDate)
            tvLastAiring.text = Helper.setTextDate(tvShow.lastAirDate)
            tvSynopsis.text = Helper.setTextString(tvShow.overview)

            // set recyclerview genre items
            val genreAdapter = GenreAdapter(tvShow.genres, this@TvShowDetailActivity)
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
                if (!tvShow.isFavorite) {
                    Toast.makeText(
                        this,
                        "${tvShow.title} has added to favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "${tvShow.title} has removed from favorites",
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
        toGenre.putExtra(GenreActivity.EXTRA_TYPE, getString(R.string.tv_shows))
        startActivity(toGenre)
    }

    companion object {
        private const val TAG = "TvShowDetailActivity"
        const val EXTRA_TV_SHOW_ID = "extra_tv_show_id"
    }

}