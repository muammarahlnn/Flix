package com.ardnn.flix.favorites

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.flix.R
import com.ardnn.flix.core.domain.model.Movie
import com.ardnn.flix.core.util.Helper
import com.ardnn.flix.databinding.ItemFavoriteBinding
import com.ardnn.flix.moviedetail.MovieDetailActivity

class FavoriteMoviesAdapter : PagedListAdapter<Movie, FavoriteMoviesAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemFavoriteBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.onBind(movie)
        }
    }

    fun getSwipedData(swipedPosition: Int): Movie? =
        getItem(swipedPosition)


    class MovieViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(movie: Movie) {
            with (binding) {
                if (movie.posterUrl.isNullOrEmpty()) {
                    ivPoster.setImageResource(R.drawable.ic_error)
                } else {
                    Helper.setImageGlide(
                        itemView.context,
                        movie.posterUrl,
                        ivPoster)
                }

                tvTitle.text = movie.title ?: "-"
                tvYear.text =
                    if (movie.releaseDate.isNullOrEmpty()) "-"
                    else movie.releaseDate.toString().substring(0, 4)
                tvRating.text = (movie.rating ?: "-").toString()
            }
            // click listener
            itemView.setOnClickListener {
                val toMovieDetail = Intent(itemView.context, MovieDetailActivity::class.java)
                toMovieDetail.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movie.id)
                itemView.context.startActivity(toMovieDetail)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}