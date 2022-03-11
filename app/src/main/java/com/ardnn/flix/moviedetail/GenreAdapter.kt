package com.ardnn.flix.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.flix.core.domain.model.Genre
import com.ardnn.flix.core.util.SingleClickListener
import com.ardnn.flix.databinding.ItemGenreBinding

class GenreAdapter(
    private val genreList: List<Genre>,
    private val clickListener: SingleClickListener<Genre>
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

        fun onBind(genre: Genre) {
            with(binding) {
                tvName.text = genre.name
            }
            itemView.setOnClickListener {
                clickListener.onItemClicked(genre)
            }
        }

    }
}