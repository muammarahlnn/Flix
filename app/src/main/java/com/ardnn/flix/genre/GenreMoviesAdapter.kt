package com.ardnn.flix.genre

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.flix.R
import com.ardnn.flix.core.data.source.local.entity.MovieEntity
import com.ardnn.flix.core.data.source.remote.ImageSize
import com.ardnn.flix.databinding.ItemFilmBinding
import com.ardnn.flix.moviedetail.MovieDetailActivity
import com.ardnn.flix.core.util.Helper

class GenreMoviesAdapter : PagedListAdapter<MovieEntity, GenreMoviesAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemFilmBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieDetail = getItem(position)
        if (movieDetail != null) {
            holder.onBind(movieDetail)
        }
    }

    class MovieViewHolder(private val binding: ItemFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(movie: MovieEntity) {
            with (binding) {
                if (movie.posterUrl.isNullOrEmpty()) {
                    ivPoster.setImageResource(R.drawable.ic_error)
                } else {
                    Helper.setImageGlide(
                        itemView.context,
                        movie.getPosterUrl(ImageSize.W342),
                        ivPoster)
                }

                tvTitle.text = movie.title ?: "-"
                tvYear.text =
                    if (movie.releaseDate.isNullOrEmpty()) "-"
                    else movie.releaseDate.toString().substring(0, 4)
                tvRating.text = (movie.rating ?: "-").toString()
            }

            itemView.setOnClickListener {
                val toMovieDetail = Intent(itemView.context, MovieDetailActivity::class.java)
                toMovieDetail.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movie.id)
                itemView.context.startActivity(toMovieDetail)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(
                oldItem: MovieEntity,
                newItem: MovieEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MovieEntity,
                newItem: MovieEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}