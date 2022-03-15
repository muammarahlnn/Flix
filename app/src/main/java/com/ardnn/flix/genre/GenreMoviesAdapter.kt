package com.ardnn.flix.genre

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.flix.core.domain.model.Movie
import com.ardnn.flix.core.util.Helper
import com.ardnn.flix.databinding.ItemFilmBinding
import com.ardnn.flix.moviedetail.MovieDetailActivity

class GenreMoviesAdapter : PagedListAdapter<Movie, GenreMoviesAdapter.MovieViewHolder>(DIFF_CALLBACK) {

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

        fun onBind(movie: Movie) {
            with (binding) {
                Helper.setImageGlide(
                    itemView.context,
                    Helper.getPosterTMDB(movie.posterUrl),
                    ivPoster
                )

                tvTitle.text = Helper.setTextString(movie.title)
                tvYear.text = Helper.setTextYear(movie.releaseDate)
                tvRating.text = Helper.setTextFloat(movie.rating)
            }

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