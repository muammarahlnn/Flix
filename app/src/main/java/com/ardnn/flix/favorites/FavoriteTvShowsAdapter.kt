package com.ardnn.flix.favorites

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.flix.R
import com.ardnn.flix.core.domain.model.TvShow
import com.ardnn.flix.core.util.Helper
import com.ardnn.flix.databinding.ItemFavoriteBinding
import com.ardnn.flix.tvshowdetail.TvShowDetailActivity

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
                if (tvShow.posterUrl.isNullOrEmpty()) {
                    ivPoster.setImageResource(R.drawable.ic_error)
                } else {
                    Helper.setImageGlide(
                        itemView.context,
                        tvShow.posterUrl,
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