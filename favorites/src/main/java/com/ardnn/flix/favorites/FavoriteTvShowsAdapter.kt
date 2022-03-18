package com.ardnn.flix.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.flix.MainFragmentDirections
import com.ardnn.flix.core.domain.tvshowdetail.model.TvShowDetail
import com.ardnn.flix.core.util.Helper
import com.ardnn.flix.databinding.ItemFavoriteBinding

class FavoriteTvShowsAdapter : PagedListAdapter<TvShowDetail, FavoriteTvShowsAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((TvShowDetail) -> Unit)? = null

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

    fun getSwipedData(swipedPosition: Int): TvShowDetail? =
        getItem(swipedPosition)

    inner class TvShowViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(tvShow: TvShowDetail) {
            with (binding) {
                Helper.setImageGlide(
                    itemView.context,
                    Helper.getPosterTMDB(tvShow.posterUrl),
                    ivPoster
                )

                tvTitle.text = Helper.setTextString(tvShow.title)
                tvYear.text = Helper.setTextYear(tvShow.firstAirDate)
                tvRating.text = Helper.setTextFloat(tvShow.rating)

                // click listener
                root.setOnClickListener {
                    onItemClick?.invoke(tvShow)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowDetail>() {
            override fun areItemsTheSame(
                oldItem: TvShowDetail,
                newItem: TvShowDetail
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TvShowDetail,
                newItem: TvShowDetail
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}