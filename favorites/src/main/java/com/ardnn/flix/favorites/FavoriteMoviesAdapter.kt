package com.ardnn.flix.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.flix.MainFragmentDirections
import com.ardnn.flix.core.domain.moviedetail.model.MovieDetail
import com.ardnn.flix.core.util.Helper
import com.ardnn.flix.databinding.ItemFavoriteBinding

class FavoriteMoviesAdapter : PagedListAdapter<MovieDetail, FavoriteMoviesAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((MovieDetail) -> Unit)? = null

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

    fun getSwipedData(swipedPosition: Int): MovieDetail? =
        getItem(swipedPosition)

    inner class MovieViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(movie: MovieDetail) {
            with (binding) {
                Helper.setImageGlide(
                    itemView.context,
                    Helper.getPosterTMDB(movie.posterUrl),
                    ivPoster
                )

                tvTitle.text = Helper.setTextString(movie.title)
                tvYear.text = Helper.setTextYear(movie.releaseDate)
                tvRating.text = Helper.setTextFloat(movie.rating)

                // click listener
                root.setOnClickListener {
                    onItemClick?.invoke(movie)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieDetail>() {
            override fun areItemsTheSame(
                oldItem: MovieDetail,
                newItem: MovieDetail
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MovieDetail,
                newItem: MovieDetail
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}