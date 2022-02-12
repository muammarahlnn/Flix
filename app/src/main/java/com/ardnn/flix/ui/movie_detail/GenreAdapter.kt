package com.ardnn.flix.ui.movie_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.flix.data.source.local.entity.GenreEntity
import com.ardnn.flix.databinding.ItemGenreBinding
import com.ardnn.flix.utils.SingleClickListener

class GenreAdapter(
    private val genreList: List<GenreEntity>,
    private val clickListener: SingleClickListener<GenreEntity>
) : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGenreBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(genreList[position])
    }

    override fun getItemCount(): Int {
        return genreList.size
    }

    inner class ViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(genre: GenreEntity) {
            with(binding) {
                tvName.text = genre.name
            }
            itemView.setOnClickListener {
                clickListener.onItemClicked(genre)
            }
        }

    }
}