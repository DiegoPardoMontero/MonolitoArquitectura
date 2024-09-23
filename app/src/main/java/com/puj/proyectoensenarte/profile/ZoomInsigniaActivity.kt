package com.puj.proyectoensenarte.profile

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityZoomInsigniaBinding

class ZoomInsigniaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityZoomInsigniaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityZoomInsigniaBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // Obtener los datos de la insignia desde el intent
        val insigniaName = intent.getStringExtra("insignia_name")
        val insigniaImageUrl = intent.getStringExtra("insignia_image")

        // Mostrar los datos de la insignia
        binding.insigniaTitle.text = insigniaName
        // Obtener la descripción de la insignia desde Firestore
        loadInsigniaDescriptionFromFirestore(insigniaName)

        
        Glide.with(this)
            .load(insigniaImageUrl)
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)  // Imagen a mostrar en caso de error
            .into(binding.insigniaImageZoom)

        // Asignar el comportamiento de cerrar la actividad
        binding.closeButton.setOnClickListener {
            finish() // Cerrar la actividad al hacer clic en el botón de cerrar
        }
        // Configurar el botón de recompensa
        binding.botonEditar.setOnClickListener {
            // Navegar a RewardProfileActivity
            val intent = Intent(this, RewardProfileActivity::class.java)
            startActivity(intent)
        }

        

        
    }

    private fun loadInsigniaDescriptionFromFirestore(insigniaName: String?) {
        val db = FirebaseFirestore.getInstance()
        db.collection("insignia").whereEqualTo("name", insigniaName)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val document = documents.documents[0]
                    val description = document.getString("description") ?: "Descripción no disponible"
                    binding.insigniaDescription.text = description
                } else {
                    binding.insigniaDescription.text = "Descripción no disponible"
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error al cargar la descripción: ${exception.message}", Toast.LENGTH_SHORT).show()
                binding.insigniaDescription.text = "Descripción no disponible"
            }
    }
}