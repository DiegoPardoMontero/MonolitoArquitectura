package com.puj.proyectoensenarte.profile



import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.puj.proyectoensenarte.R

data class Insignia(val imageUrl: String, val name: String)

class InsigniaAdapter(
    private val context: Context,
    private var insignias: List<Insignia>,
    private val onItemClicked: (Insignia) -> Unit
) : RecyclerView.Adapter<InsigniaAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.insigniaImage)
        val name: TextView = view.findViewById(R.id.insigniaText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_insignia, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val insignia = insignias[position]

        // Usar Glide para cargar la imagen desde la URL
        Glide.with(context)
            .load(insignia.imageUrl)  // Cargar imagen desde la URL
            .placeholder(R.drawable.img_placeholder)  // Imagen de carga por defecto
            .into(holder.icon)

        holder.name.text = insignia.name  // Setear el nombre

        // Manejar clic en cada ítem
        holder.itemView.setOnClickListener {
            onItemClicked(insignia)

            val intent = Intent(context, ZoomInsigniaActivity::class.java)
            intent.putExtra("insignia_name", insignia.name)
            intent.putExtra("insignia_image", insignia.imageUrl)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return insignias.size
    }

    // Método para actualizar los datos del adaptador
    fun updateData(newInsignias: List<Insignia>) {
        insignias = newInsignias
        notifyDataSetChanged()
    }
}