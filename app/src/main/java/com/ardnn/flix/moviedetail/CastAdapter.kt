package com.ardnn.flix.moviedetail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardnn.flix.R
import com.ardnn.flix.core.domain.moviedetail.model.Cast
import com.ardnn.flix.core.util.Helper
import com.ardnn.flix.databinding.ItemCastBinding

class CastAdapter : PagedListAdapter<Cast, CastAdapter.CastViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val binding = ItemCastBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val cast = getItem(position)
        if (cast != null) {
            holder.onBind(cast)
        }
    }

    class CastViewHolder(private val binding: ItemCastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(cast: Cast) {
            with (binding) {
                Helper.setImageGlide(
                    itemView.context,
                    Helper.getProfilePhotoTMDB(cast.profileUrl),
                    ivProfile
                )
                tvName.text = Helper.setTextString(cast.name)
                tvCharacter.text = Helper.setTextString(
                    itemView.context.getString(R.string.as_character, cast.character))
            }

            itemView.setOnClickListener {
                Toast.makeText(
                    itemView.context,
                    "Person detail is under development",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cast>() {
            override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean =
                oldItem == newItem

        }
    }
}