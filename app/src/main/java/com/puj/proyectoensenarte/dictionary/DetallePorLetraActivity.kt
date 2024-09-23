package com.puj.proyectoensenarte.dictionary

import PalabraAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.databinding.ActivityDetallePorLetraBinding

class DetallePorLetraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetallePorLetraBinding
    private lateinit var palabraAdapter: PalabraAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallePorLetraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val letra = intent.getStringExtra("LETRA") ?: "A"

        setupUI(letra)
        setupRecyclerView()
        loadPalabras(letra)
    }

    private fun setupRecyclerView() {
        palabraAdapter = PalabraAdapter { palabra ->
            navigateToDetallePalabra(palabra)
        }
        binding.rvPalabras.apply {
            layoutManager = LinearLayoutManager(this@DetallePorLetraActivity)
            adapter = palabraAdapter
        }
    }

    private fun loadPalabras(letra: String) {
        db.collection("dictionary").document("palabras")
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val todasLasPalabras = document.data?.mapValues { it.value.toString() } ?: emptyMap()
                    val palabrasFiltradas = todasLasPalabras.filter { it.value.startsWith(letra, ignoreCase = true) }
                    val listaPalabras = palabrasFiltradas.map { Palabra(it.key, it.value) }
                    palabraAdapter.submitList(listaPalabras)
                } else {
                    // Manejar el caso en que no se encuentren palabras
                }
            }
            .addOnFailureListener { exception ->
                // Manejar el error
            }
    }

    private fun navigateToDetallePalabra(palabra: String) {
        val intent = Intent(this, DetallePalabraActivity::class.java).apply {
            putExtra("PALABRA", palabra)
        }
        startActivity(intent)
    }

    private fun setupUI(letra: String) {
        binding.tvLetraTitulo.text = "Letra $letra"

        val signImageUrl = intent.getStringExtra("SIGN_IMAGE_URL") ?: "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/temp%2Fa_sin_fondo.png?alt=media&token=992184ff-f776-4b57-a6d6-93606df3676a"
        if (signImageUrl != null) {
            Glide.with(this)
                .load(signImageUrl)
                .into(binding.ivSignIcon)
        }
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}