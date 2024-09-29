package com.puj.proyectoensenarte.dictionary.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.puj.proyectoensenarte.databinding.ActivityResultadoBusquedaCategoriaBinding
import com.puj.proyectoensenarte.dictionary.adapters.CategoryAdapter

class ResultadoBusquedaCategoriaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultadoBusquedaCategoriaBinding
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultadoBusquedaCategoriaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            finish()
        }

        setupSearchBar()
        setupRecyclerView()

        val searchQuery = intent.getStringExtra("CATEGORIA") ?: ""
    }

    private fun setupSearchBar() {
        binding.ivSearch.setOnClickListener {
            performSearch2()
        }
    }

    private fun setupRecyclerView() {
        categoryAdapter = CategoryAdapter { category ->
            navigateToDetallePorCategoria(category.name, category.imageUrl)
        }
        binding.rvSearchResults.adapter = categoryAdapter
        binding.rvSearchResults.layoutManager = LinearLayoutManager(this)
    }



    private fun performSearch2() {
        var searchQuery = binding.etSearch.text.toString().trim()
        searchQuery = searchQuery.lowercase()
        if (searchQuery.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese un término de búsqueda", Toast.LENGTH_SHORT).show()
            return
        }
    }

    private fun navigateToError404() {
        val intent = Intent(this, Error404Activity::class.java)
        startActivity(intent)
    }

    private fun capitalizeFirstLetter(input: String): String {
        return if (input.isNotEmpty()) {
            input.substring(0, 1).uppercase() + input.substring(1).lowercase()
        } else {
            input
        }
    }

    private fun navigateToDetallePorCategoria(categoria: String, imageUri: String) {
        val intent = Intent(this, DetallePorCategoriaActivity::class.java).apply {
            putExtra("CATEGORIA", categoria)
            putExtra("CATEGORIA_IMAGE_URI", imageUri)
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
