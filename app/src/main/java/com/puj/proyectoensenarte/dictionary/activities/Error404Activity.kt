package com.puj.proyectoensenarte.dictionary.activities

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.databinding.ActivityError404Binding

class Error404Activity : AppCompatActivity() {

    private lateinit var binding: ActivityError404Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityError404Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSearchBar()
    }

    private fun setupSearchBar() {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                true
            } else {
                false
            }
        }

        binding.ivSearch.setOnClickListener {
            performSearch()
        }
    }

    private fun performSearch() {
        // Lógica para la búsqueda
        val searchQuery = binding.etSearch.text.toString()
        binding.etSearch.setText("")
        hideKeyboard()

        // Aquí puedes agregar la lógica para manejar la búsqueda
        // Por ejemplo, puedes iniciar una nueva actividad con los resultados de la búsqueda
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }
}