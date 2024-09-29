package com.puj.proyectoensenarte.dictionary.adapters

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.dictionary.data.Category

class CategoryAdapter(
    private val onItemClicked: (Category) -> Unit
) : ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.imgCategoryIcon)
        val name: TextView = view.findViewById(R.id.tvCategoryName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = getItem(position)

        val context = holder.itemView.context
        val resourceId = context.resources.getIdentifier(category.imageUrl, "drawable", context.packageName)

        holder.icon.setImageResource(resourceId)
        holder.name.text = category.name

        holder.itemView.setOnClickListener {
            onItemClicked(category)
        }
    }

    fun loadCategoriesFromDatabase(context: Context, db: SQLiteDatabase) {
        val cursor: Cursor = db.rawQuery("SELECT nombre, imagen FROM categoria", null)
        val categories = mutableListOf<Category>()

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val image = cursor.getString(cursor.getColumnIndexOrThrow("imagen"))
                categories.add(Category(image, name))
            } while (cursor.moveToNext())
        }
        cursor.close()

        submitList(categories)
    }

    private class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
}
