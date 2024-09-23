package com.puj.proyectoensenarte.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puj.proyectoensenarte.R

class AvatarAdapter(
    private val avatarUrls: List<String>,
    private val onAvatarSelected: (String) -> Unit
) : RecyclerView.Adapter<AvatarAdapter.AvatarViewHolder>() {

    inner class AvatarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.avatarImage)

        init {
            itemView.setOnClickListener {
                onAvatarSelected(avatarUrls[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_avatar, parent, false)
        return AvatarViewHolder(view)
    }

    override fun onBindViewHolder(holder: AvatarViewHolder, position: Int) {
        val avatarUrl = avatarUrls[position]
        Glide.with(holder.itemView.context)
            .load(avatarUrl)
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .into(holder.imageView)
    }

    override fun getItemCount() = avatarUrls.size
}