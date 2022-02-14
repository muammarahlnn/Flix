package com.ardnn.flix.ui.favorites

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.flix.R
import com.ardnn.flix.data.source.local.entity.MovieDetailEntity
import com.ardnn.flix.data.source.remote.ImageSize
import com.ardnn.flix.databinding.ItemFavoriteBinding
import com.ardnn.flix.ui.movie_detail.MovieDetailActivity
import com.ardnn.flix.utils.Helper

class FavoriteMoviesAdapter : PagedListAdapter<MovieDetailEntity, FavoriteMoviesAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemFavoriteBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieDetail = getItem(position)
        if (movieDetail != null) {
            holder.onBind(movieDetail)
        }
    }

    fun getSwipedData(swipedPosition: Int): MovieDetailEntity? =
        getItem(swipedPosition)


    class MovieViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(movieDetail: MovieDetailEntity) {
            with (binding) {
                if (movieDetail.posterUrl.isNullOrEmpty()) {
                    ivPoster.setImageResource(R.drawable.ic_error)
                } else {
                    Helper.setImageGlide(
                        itemView.context,
                        movieDetail.getPosterUrl(ImageSize.W200),
                        ivPoster)
                }

                tvTitle.text = movieDetail.title ?: "-"
                tvYear.text =
                    if (movieDetail.releaseDate.isNullOrEmpty()) "-"
                    else movieDetail.releaseDate.toString().substring(0, 4)
                tvRating.text = (movieDetail.rating ?: "-").toString()
            }
            // click listener
            itemView.setOnClickListener {
                val toMovieDetail = Intent(itemView.context, MovieDetailActivity::class.java)
                toMovieDetail.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movieDetail.id)
                itemView.context.startActivity(toMovieDetail)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieDetailEntity>() {
            override fun areItemsTheSame(
                oldItem: MovieDetailEntity,
                newItem: MovieDetailEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MovieDetailEntity,
                newItem: MovieDetailEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}