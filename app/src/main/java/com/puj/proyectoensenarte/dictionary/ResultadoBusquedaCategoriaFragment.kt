package com.puj.proyectoensenarte.dictionary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.puj.proyectoensenarte.databinding.ActivityResultadoBusquedaCategoriaBinding

class ResultadoBusquedaCategoriaFragment : Fragment() {

    private var _binding: ActivityResultadoBusquedaCategoriaBinding? = null
    private val binding get() = _binding!!
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ActivityResultadoBusquedaCategoriaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchBar()
        setupRecyclerView()
        performSearch(arguments?.getString("SEARCH_QUERY") ?: "")
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
        binding.rvSearchResults.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun performSearch(query: String) {
        var capitalizedQuery = capitalizeFirstLetter(query)
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

    private fun navigateToDetallePorCategoria(categoria: String, imageUrl : String) {
        val intent = Intent(requireContext(), DetallePorCategoriaActivity::class.java).apply {
            putExtra("CATEGORIA", categoria)
            putExtra("CATEGORIA_IMAGE_URL", imageUrl)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(searchQuery: String): ResultadoBusquedaCategoriaFragment {
            return ResultadoBusquedaCategoriaFragment().apply {
                arguments = Bundle().apply {
                    putString("SEARCH_QUERY", searchQuery)
                }
            }
        }
    }
}