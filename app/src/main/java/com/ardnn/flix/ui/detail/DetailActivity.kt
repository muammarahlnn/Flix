package com.ardnn.flix.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.ardnn.flix.R
import com.ardnn.flix.data.FilmEntity
import com.ardnn.flix.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_FILM = "extra_film"
    }

    private lateinit var viewModel: DetailViewModel
    private lateinit var binding: ActivityDetailBinding

    private var isSynopsisExtended = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // get film and set it to view model
        val film = intent.getParcelableExtra<FilmEntity>(EXTRA_FILM) as FilmEntity
        viewModel = ViewModelProvider(this, DetailViewModelFactory(film))[DetailViewModel::class.java]

        // set film data
        viewModel.film.observe(this, { itFilm ->
            setFilmDataToWidgets(itFilm)
        })

        viewModel.isSynopsisExtended.observe(this, { isExtended ->
            with (binding) {
                if (isExtended) {
                    tvSynopsis.maxLines = Int.MAX_VALUE
                    binding.tvMore.text = resources.getString(R.string.less)
                } else {
                    tvSynopsis.maxLines = 2
                    binding.tvMore.text = resources.getString(R.string.more)
                }
            }
        })

        // click listener
        binding.btnBack.setOnClickListener(this)
        binding.clWrapperSynopsis.setOnClickListener(this)
    }

    private fun setFilmDataToWidgets(film: FilmEntity) {
        with (binding) {
            ivWallpaper.setImageResource(film.poster)
            ivPoster.setImageResource(film.poster)
            tvTitle.text = film.title
            tvReleaseDate.text = film.releaseDate
            tvRuntime.text = resources.getString(R.string.minutes, film.runtime)
            tvRating.text = film.rating.toString()
            tvSynopsis.text = film.overview
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