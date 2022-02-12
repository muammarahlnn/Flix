package com.ardnn.flix.ui.genre

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.flix.R
import com.ardnn.flix.data.source.local.entity.TvShowDetailEntity
import com.ardnn.flix.data.source.remote.ImageSize
import com.ardnn.flix.databinding.ItemFilmBinding
import com.ardnn.flix.ui.tvshow_detail.TvShowDetailActivity
import com.ardnn.flix.utils.Helper

class GenreTvShowsAdapter : PagedListAdapter<TvShowDetailEntity, GenreTvShowsAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val binding = ItemFilmBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShowDetail = getItem(position)
        if (tvShowDetail != null) {
            holder.onBind(tvShowDetail)
        }
    }

    class TvShowViewHolder(private val binding: ItemFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(tvShowDetail: TvShowDetailEntity) {
            with (binding) {
                if (tvShowDetail.posterUrl.isNullOrEmpty()) {
                    ivPoster.setImageResource(R.drawable.ic_error)
                } else {
                    Helper.setImageGlide(
                        itemView.context,
                        tvShowDetail.getPosterUrl(ImageSize.W342),
                        ivPoster)
                }

                tvTitle.text = tvShowDetail.title ?: "-"
                tvYear.text =
                    if (tvShowDetail.firstAirDate.isNullOrEmpty()) "-"
                    else tvShowDetail.firstAirDate.toString().substring(0, 4)
                tvRating.text = (tvShowDetail.rating ?: "-").toString()
            }

            itemView.setOnClickListener {
                val toTvShowDetail = Intent(itemView.context, TvShowDetailActivity::class.java)
                toTvShowDetail.putExtra(TvShowDetailActivity.EXTRA_TV_SHOW_ID, tvShowDetail.id)
                itemView.context.startActivity(toTvShowDetail)
            }
        }
    }

}