package com.puj.proyectoensenarte.dictionary.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.puj.proyectoensenarte.databinding.ActivityResultadoBusquedaCategoriaBinding
import com.puj.proyectoensenarte.dictionary.adapters.CategoryAdapter
import com.puj.proyectoensenarte.dictionary.data.Category

class ResultadoBusquedaCategoriaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultadoBusquedaCategoriaBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private val db = FirebaseFirestore.getInstance()

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
        performSearch(searchQuery)
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

    private fun performSearch2() {
        var searchQuery = binding.etSearch.text.toString().trim()
        searchQuery = searchQuery.lowercase()
        if (searchQuery.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese un término de búsqueda", Toast.LENGTH_SHORT).show()
            return
        }

        db.collection("dict").document(searchQuery).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    performSearch(searchQuery)
                }
                else{
                    navigateToError404()
                }
            }
            .addOnFailureListener { e ->
                Log.e("DictionaryActivity", "Error buscando categoría: ", e)
                navigateToError404()
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
