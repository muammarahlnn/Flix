package com.ardnn.flix.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.flix.R
import com.ardnn.flix.core.domain.model.Movie
import com.ardnn.flix.core.util.Helper
import com.ardnn.flix.databinding.ItemFilmBinding

class TempMoviesAdapter : RecyclerView.Adapter<TempMoviesAdapter.ViewHolder>() {

    private var listData = ArrayList<Movie>()
    var onItemClick: ((Movie) -> Unit)? = null

    fun setData(newListData: List<Movie>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_film, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listData.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemFilmBinding.bind(itemView)
        fun bind(movie: Movie) {
            with (binding) {
                Helper.setImageGlide(
                    itemView.context,
                    movie.posterUrl,
                    ivPoster
                )

                tvTitle.text = Helper.setTextString(movie.title)
                tvYear.text = Helper.setTextYear(movie.releaseDate)
                tvRating.text = Helper.setTextFloat(movie.rating)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[absoluteAdapterPosition])
            }
        }
    }
}