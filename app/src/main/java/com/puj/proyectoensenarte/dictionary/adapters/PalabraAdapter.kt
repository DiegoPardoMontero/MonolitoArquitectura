package com.puj.proyectoensenarte.dictionary.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.dictionary.data.Palabra


class PalabraAdapter(private val onPalabraClick: (String) -> Unit) : ListAdapter<Palabra, PalabraAdapter.PalabraViewHolder>(
    PalabraDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PalabraViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_palabra, parent, false)
        return PalabraViewHolder(view)
    }

    override fun onBindViewHolder(holder: PalabraViewHolder, position: Int) {
        val palabra = getItem(position)
        holder.bind(palabra)
        holder.itemView.setOnClickListener { onPalabraClick(palabra.texto) }
    }

    class PalabraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvPalabra: TextView = itemView.findViewById(R.id.tvPalabra)

        fun bind(palabra: Palabra) {
            tvPalabra.text = palabra.texto
        }
    }

    class PalabraDiffCallback : DiffUtil.ItemCallback<Palabra>() {
        override fun areItemsTheSame(oldItem: Palabra, newItem: Palabra): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Palabra, newItem: Palabra): Boolean {
            return oldItem == newItem
        }
    }
}