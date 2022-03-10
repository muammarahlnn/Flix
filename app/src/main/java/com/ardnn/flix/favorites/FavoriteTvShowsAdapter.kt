package com.ardnn.flix.favorites

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.flix.R
import com.ardnn.flix.core.data.source.local.entity.TvShowEntity
import com.ardnn.flix.core.data.source.remote.ImageSize
import com.ardnn.flix.databinding.ItemFavoriteBinding
import com.ardnn.flix.tvshowdetail.TvShowDetailActivity
import com.ardnn.flix.core.util.Helper

class FavoriteTvShowsAdapter : PagedListAdapter<TvShowEntity, FavoriteTvShowsAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

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

    fun getSwipedData(swipedPosition: Int): TvShowEntity? =
        getItem(swipedPosition)

    class TvShowViewHolder(private val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(tvShow: TvShowEntity) {
            with (binding) {
                if (tvShow.posterUrl.isNullOrEmpty()) {
                    ivPoster.setImageResource(R.drawable.ic_error)
                } else {
                    Helper.setImageGlide(
                        itemView.context,
                        tvShow.getPosterUrl(ImageSize.W200),
                        ivPoster)
                }

                tvTitle.text = tvShow.title ?: "-"
                tvYear.text =
                    if (tvShow.firstAirDate.isNullOrEmpty()) "-"
                    else tvShow.firstAirDate.toString().substring(0, 4)
                tvRating.text = (tvShow.rating ?: "-").toString()
            }
            // click listener
            itemView.setOnClickListener {
                val toTvShowDetail = Intent(itemView.context, TvShowDetailActivity::class.java)
                toTvShowDetail.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW_ID, tvShow.id)
                itemView.context.startActivity(toTvShowDetail)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvShowEntity>() {
            override fun areItemsTheSame(
                oldItem: TvShowEntity,
                newItem: TvShowEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TvShowEntity,
                newItem: TvShowEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}