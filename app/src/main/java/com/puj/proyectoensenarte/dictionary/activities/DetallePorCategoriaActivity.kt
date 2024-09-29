package com.puj.proyectoensenarte.dictionary.activities

import com.puj.proyectoensenarte.dictionary.adapters.PalabraAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.puj.proyectoensenarte.databinding.ActivityDetallePorCategoriaBinding

class DetallePorCategoriaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetallePorCategoriaBinding
    private lateinit var palabraAdapter: PalabraAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePorCategoriaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoria = intent.getStringExtra("CATEGORIA") ?: "Desconocida"
        val categoriaImageUrl = intent.getStringExtra("CATEGORIA_IMAGE_URL")

        setupUI(categoria, categoriaImageUrl)
        setupRecyclerView()
    }

    private fun setupUI(categoria: String, categoriaImageUrl: String?) {
        binding.tvCategoriaTitulo.text = categoria

        categoriaImageUrl?.let {
            Glide.with(this)
                .load(it)
                .into(binding.ivCategoriaIcon)
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        palabraAdapter = PalabraAdapter { palabra ->
            navigateToDetallePalabra(palabra)
        }
        binding.rvPalabrasCategoria.apply {
            layoutManager = LinearLayoutManager(this@DetallePorCategoriaActivity)
            adapter = palabraAdapter
        }
    }

    private fun navigateToDetallePalabra(palabra: String) {
        val intent = Intent(this, DetallePalabraActivity::class.java).apply {
            putExtra("PALABRA", palabra)
        }
        startActivity(intent)
    }


    fun primeraLetraMinuscula(texto: String): String {
        return texto.replaceFirstChar { it.lowercase() }
    }
}
