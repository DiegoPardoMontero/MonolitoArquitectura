package com.puj.proyectoensenarte.dictionary

import AlphabetAdapter
import Error404
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityDictionaryFragmentBinding

class DictionaryFragmentActivity : Fragment() {

    private var binding: ActivityDictionaryFragmentBinding? = null
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var alphabetAdapter: AlphabetAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityDictionaryFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchBar()
        setupCategoriesRecyclerView()
        setupAlphabetRecyclerView()
        loadCategories()
    }

    private fun setupSearchBar() {
        binding?.etSearch?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                true
            } else {
                false
            }
        }

        binding?.ivSearch?.setOnClickListener {
            performSearch()
        }
    }

    private fun setupAlphabetRecyclerView() {
        val letters = ('A'..'Z').map { it.toString() }
        alphabetAdapter = AlphabetAdapter(letters) { letter ->
            navigateToDetallePorLetra(letter)
        }
        binding?.rvAlphabet?.apply {
            adapter = alphabetAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    private fun navigateToDetallePorLetra(letter: String) {
        val storage = FirebaseStorage.getInstance()
        val imageRef = storage.reference.child("temp/$letter.png")

        imageRef.downloadUrl.addOnSuccessListener { uri ->
            val intent = Intent(requireContext(), DetallePorLetraActivity::class.java).apply {
                putExtra("LETRA", letter)
                putExtra("SIGN_IMAGE_URL", uri.toString())
            }
            startActivity(intent)
        }.addOnFailureListener { exception ->
            Log.e("DictionaryFragment", "Error al obtener la URL de la imagen: ${exception.message}")
            val intent = Intent(requireContext(), DetallePorLetraActivity::class.java).apply {
                putExtra("LETRA", letter)
            }
            startActivity(intent)
        }
    }

    private fun navigateToDetallePorCategoria(categoria: String, imageUrl : String) {
        val intent = Intent(requireContext(), DetallePorCategoriaActivity::class.java).apply {
            putExtra("CATEGORIA", categoria)
            putExtra("CATEGORIA_IMAGE_URL", imageUrl)
        }
        startActivity(intent)
    }

    private fun setupCategoriesRecyclerView() {
        categoryAdapter = CategoryAdapter { category ->
            navigateToDetallePorCategoria(category.name, category.imageUrl)
        }
        binding?.rvCategories?.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    private fun loadCategories() {
        categoryAdapter.loadCategories(
            onSuccess = {
                // Categorías cargadas exitosamente
            },
            onFailure = { exception ->
                // Manejar el error
            }
        )
    }

    private fun performSearch() {
        var searchQuery = binding?.etSearch?.text.toString().trim()
        searchQuery = searchQuery.lowercase()
        if (searchQuery.isEmpty()) {
            Toast.makeText(context, "Por favor, ingrese un término de búsqueda", Toast.LENGTH_SHORT).show()
            return
        }

        hideKeyboard()

        // Primero, buscar en las categorías
        db.collection("dict").document(searchQuery).get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Es una categoría
                    navigateToResultadoBusquedaCategoria(searchQuery)
                } else {
                    //Es una palabra
                    searchQuery = capitalizeFirstLetter(searchQuery)
                    searchInPalabras(searchQuery)
                }
            }
            .addOnFailureListener { e ->
                Log.e("DictionaryFragment", "Error buscando categoría: ", e)
                navigateToError404()
            }
    }

    private fun searchInPalabras(searchQuery: String) {
        db.collection("dictionary").document("palabras").get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val palabras = document.data
                    if (palabras?.containsValue(searchQuery) == true) {
                        // La palabra existe
                        navigateToResultadoBusquedaPalabra(searchQuery)
                    } else {
                        navigateToError404()
                    }
                } else {
                    // El documento "palabras" no existe
                    navigateToError404()
                }
            }
            .addOnFailureListener { e ->
                navigateToError404()
            }
    }

    private fun navigateToResultadoBusquedaCategoria(categoria: String) {
        var categoria = capitalizeFirstLetter(categoria)
        val fragment = ResultadoBusquedaCategoriaFragment.newInstance(categoria)
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToResultadoBusquedaPalabra(palabra: String) {
        val fragment = ResultadoBusquedaPalabraFragment.newInstance(palabra)
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun noResultsFound(query: String): Boolean {
        // Implementar la lógica si no se encuentran resultados
        return false
    }

    private fun navigateToError404() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, Error404())
            .addToBackStack(null)
            .commit()
    }

    private fun hideKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding?.etSearch?.windowToken, 0)
    }

    private fun capitalizeFirstLetter(input: String): String {
        return if (input.isNotEmpty()) {
            input.substring(0, 1).uppercase() + input.substring(1).lowercase()
        } else {
            input
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}
