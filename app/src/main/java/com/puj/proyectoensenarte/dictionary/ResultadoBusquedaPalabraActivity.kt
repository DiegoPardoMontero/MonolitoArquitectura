package com.puj.proyectoensenarte.dictionary

import PalabraAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.databinding.ActivityResultadoBusquedaPalabraBinding

class ResultadoBusquedaPalabraFragment : Fragment() {

    private var _binding: ActivityResultadoBusquedaPalabraBinding? = null
    private val binding get() = _binding!!
    private lateinit var palabraAdapter: PalabraAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ActivityResultadoBusquedaPalabraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchBar()
        setupRecyclerView()
        performSearch(arguments?.getString("SEARCH_QUERY") ?: "")
    }

    private fun setupSearchBar() {
        binding.etSearch.setText(arguments?.getString("SEARCH_QUERY"))
        binding.ivSearch.setOnClickListener {
            performSearch(binding.etSearch.text.toString())
        }
    }

    private fun setupRecyclerView() {
        palabraAdapter = PalabraAdapter { palabra ->
            navigateToDetallePalabra(palabra)
        }
        binding.rvSearchResults.adapter = palabraAdapter
        binding.rvSearchResults.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun navigateToDetallePalabra(palabra: String) {
        val intent = Intent(requireContext(), DetallePalabraActivity::class.java).apply {
            putExtra("PALABRA", palabra)
        }
        startActivity(intent)
    }

    private fun performSearch(query: String) {
        db.collection("dictionary").document("palabras")
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val palabras = document.data?.filter { (key, value) ->
                        key.contains(query, ignoreCase = true) ||
                                (value as? String)?.contains(query, ignoreCase = true) == true
                    }?.map { (key, value) ->
                        Palabra(key, value.toString())
                    } ?: listOf()
                    palabraAdapter.submitList(palabras)
                }
            }
            .addOnFailureListener { exception ->
                // Manejar el error
                // Puedes mostrar un mensaje de error o realizar alguna acción específica
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(searchQuery: String): ResultadoBusquedaPalabraFragment {
            return ResultadoBusquedaPalabraFragment().apply {
                arguments = Bundle().apply {
                    putString("SEARCH_QUERY", searchQuery)
                }
            }
        }
    }
}