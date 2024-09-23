package com.puj.proyectoensenarte.dictionary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.puj.proyectoensenarte.databinding.ActivityResultadoBusquedaCategoriaBinding
import com.puj.proyectoensenarte.dictionary.adapters.CategoryAdapter
import com.puj.proyectoensenarte.dictionary.data.Category

class ResultadoBusquedaCategoriaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultadoBusquedaCategoriaBinding
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultadoBusquedaCategoriaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSearchBar()
        setupRecyclerView()

        val searchQuery = intent.getStringExtra("CATEGORIA") ?: ""
        performSearch(searchQuery)
    }

    private fun setupSearchBar() {
        binding.ivSearch.setOnClickListener {
            performSearch(binding.etSearch.text.toString())
        }
    }

    private fun setupRecyclerView() {
        categoryAdapter = CategoryAdapter { category ->
            navigateToDetallePorCategoria(category.name, category.imageUrl)
        }
        binding.rvSearchResults.adapter = categoryAdapter
        binding.rvSearchResults.layoutManager = LinearLayoutManager(this)
    }

    private fun performSearch(query: String) {
        val capitalizedQuery = capitalizeFirstLetter(query)
        val storage = FirebaseStorage.getInstance()
        val imageRef = storage.reference.child("imagenesCategorias/$capitalizedQuery.png")

        imageRef.downloadUrl.addOnSuccessListener { uri ->
            val downloadUrl = uri.toString()
            val formattedQuery = capitalizedQuery.replace(Regex("(?<=.)([A-Z])"), " $1")
            val category = Category(downloadUrl, formattedQuery)
            categoryAdapter.submitList(listOf(category))
        }.addOnFailureListener { exception ->
            Log.e("ResultadoBusqueda", "Error al cargar la imagen de la categoría: ${exception.message}")
            val category = Category("url_imagen_por_defecto", capitalizedQuery)
            categoryAdapter.submitList(listOf(category))
        }
    }

    private fun capitalizeFirstLetter(input: String): String {
        return if (input.isNotEmpty()) {
            input.substring(0, 1).uppercase() + input.substring(1).lowercase()
        } else {
            input
        }
    }

    private fun navigateToDetallePorCategoria(categoria: String, imageUrl: String) {
        val intent = Intent(this, DetallePorCategoriaActivity::class.java).apply {
            putExtra("CATEGORIA", categoria)
            putExtra("CATEGORIA_IMAGE_URL", imageUrl)
        }
        startActivity(intent)
    }

    companion object {
        fun newIntent(context: AppCompatActivity, searchQuery: String): Intent {
            return Intent(context, ResultadoBusquedaCategoriaActivity::class.java).apply {
                putExtra("SEARCHQUERY", searchQuery)
            }
        }
    }
}
