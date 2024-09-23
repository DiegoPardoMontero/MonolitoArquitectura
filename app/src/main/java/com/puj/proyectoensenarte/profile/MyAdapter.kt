package com.puj.proyectoensenarte.profile

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.puj.proyectoensenarte.MainActivity
import com.puj.proyectoensenarte.R

import com.puj.proyectoensenarte.databinding.ItemLayoutBinding


class MyAdapter(context: Context?, private val mData: List<String>) :

    RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater


    init {
        mInflater = LayoutInflater.from(context)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemLayoutBinding = ItemLayoutBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mData[position]

        // Establecer el texto en el TextView existente
        holder.binding.textView.text = item

        // Cambiar el subtítulo según la posición
        when (position) {
            0 -> holder.binding.subtitleTextView.text = "Nombre,correo electónico y usuario"
            1 -> holder.binding.subtitleTextView.text = "Estadísticas e insignias obtenidas a lo largo del aprendizaje"
            2 -> holder.binding.subtitleTextView.text = "Cierra tu sesión y vuelve cuando quieras"
        }

        // Cambiar el ícono según la posición
        when (position) {
            0 -> holder.binding.imageView.setImageResource(R.drawable.ic_settings)
            1 -> holder.binding.imageView.setImageResource(R.drawable.ic_statistics)
            2 -> holder.binding.imageView.setImageResource(R.drawable.ic_logout)
            else -> holder.binding.imageView.setImageResource(R.drawable.ic_logout)
        }

        // Configurar el OnClickListener para redirigir a otra pantalla
        holder.binding.goIcon.setOnClickListener {
            val context = holder.itemView.context
            val intent: Intent = when (position) {
                0 -> Intent(context, SettingsProfileActivity::class.java) // Reemplaza con la actividad que deseas abrir
                1 -> Intent(context, StatisticsProfileActivity::class.java) // Reemplaza con la actividad que deseas abrir
                2 -> Intent(context, MainActivity::class.java) // Reemplaza con la actividad que deseas abrir
                else -> Intent(context, MainActivity::class.java) // Actividad por defecto si fuera necesario
            }
            context.startActivity(intent)
        }



        // Establecer la imagen del nuevo ImageView (flecha)
        holder.binding.goIcon.setImageResource(R.drawable.ic_arrow_right)
    }

    override fun getItemCount(): Int {
        return mData.size
    }


    inner class ViewHolder internal constructor(binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: ItemLayoutBinding

        init {
            this.binding = binding
        }
    }
}

