package com.ardnn.flix.ui.favorites

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.flix.R
import com.ardnn.flix.data.source.local.entity.TvShowDetailEntity
import com.ardnn.flix.data.source.remote.ImageSize
import com.ardnn.flix.databinding.ItemFavoriteBinding
import com.ardnn.flix.ui.tvshow_detail.TvShowDetailActivity
import com.ardnn.flix.utils.Helper

class FavoriteTvShowsAdapter : PagedListAdapter<TvShowDetailEntity, FavoriteTvShowsAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val binding = ItemFavoriteBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShowDetail = getItem(position)
        if (tvShowDetail != null) {
            holder.onBind(tvShowDetail)
        }
    }

    fun getSwipedData(swipedPosition: Int): TvShowDetailEntity? =
        getItem(swipedPosition)

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowDetailEntity>() {
            override fun areItemsTheSame(
                oldItem: TvShowDetailEntity,
                newItem: TvShowDetailEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TvShowDetailEntity,
                newItem: TvShowDetailEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    class TvShowViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(tvShowDetail: TvShowDetailEntity) {
            with (binding) {
                if (tvShowDetail.posterUrl.isNullOrEmpty()) {
                    ivPoster.setImageResource(R.drawable.ic_error)
                } else {
                    Helper.setImageGlide(
                        itemView.context,
                        tvShowDetail.getPosterUrl(ImageSize.W200),
                        ivPoster)
                }

                tvTitle.text = tvShowDetail.title ?: "-"
                tvYear.text =
                    if (tvShowDetail.firstAirDate.isNullOrEmpty()) "-"
                    else tvShowDetail.firstAirDate.toString().substring(0, 4)
                tvRating.text = (tvShowDetail.rating ?: "-").toString()
            }
            // click listener
            itemView.setOnClickListener {
                val toTvShowDetail = Intent(itemView.context, TvShowDetailActivity::class.java)
                toTvShowDetail.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW_ID, tvShowDetail.id)
                itemView.context.startActivity(toTvShowDetail)
            }
        }
    }
}