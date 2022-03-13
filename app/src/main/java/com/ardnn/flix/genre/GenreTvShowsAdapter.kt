package com.ardnn.flix.genre

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.flix.core.domain.model.TvShow
import com.ardnn.flix.core.util.Helper
import com.ardnn.flix.databinding.ItemFilmBinding
import com.ardnn.flix.tvshowdetail.TvShowDetailActivity

class GenreTvShowsAdapter : PagedListAdapter<TvShow, GenreTvShowsAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val binding = ItemFilmBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.onBind(tvShow)
        }
    }

    class TvShowViewHolder(private val binding: ItemFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(tvShow: TvShow) {
            with (binding) {
                Helper.setImageGlide(
                    itemView.context,
                    tvShow.posterUrl,
                    ivPoster
                )

                tvTitle.text = Helper.setTextString(tvShow.title)
                tvYear.text = Helper.setTextYear(tvShow.firstAirDate)
                tvRating.text = Helper.setTextFloat(tvShow.rating)
            }

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