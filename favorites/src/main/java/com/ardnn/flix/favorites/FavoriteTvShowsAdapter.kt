package com.ardnn.flix.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.flix.core.domain.model.TvShow
import com.ardnn.flix.core.util.Helper
import com.ardnn.flix.databinding.ItemFavoriteBinding

class FavoriteTvShowsAdapter : PagedListAdapter<TvShow, FavoriteTvShowsAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val binding = ItemFavoriteBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.onBind(tvShow)
        }
    }

    fun getSwipedData(swipedPosition: Int): TvShow? =
        getItem(swipedPosition)

    class TvShowViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(tvShow: TvShow) {
            with (binding) {
                Helper.setImageGlide(
                    itemView.context,
                    Helper.getPosterTMDB(tvShow.posterUrl),
                    ivPoster
                )

                tvTitle.text = Helper.setTextString(tvShow.title)
                tvYear.text = Helper.setTextYear(tvShow.firstAirDate)
                tvRating.text = Helper.setTextFloat(tvShow.rating)
            }

            // click listener
//            itemView.setOnClickListener { view ->
//                val toTvShowDetail = FavoritesPagerFragmentDirections
//                    .actionNavigationFavoritesToTvShowDetailActivity().apply {
//                        tvShowId = tvShow.id
//                    }
//                view.findNavController().navigate(toTvShowDetail)
//            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(
                oldItem: TvShow,
                newItem: TvShow
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TvShow,
                newItem: TvShow
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}