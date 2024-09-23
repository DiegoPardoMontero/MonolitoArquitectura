package com.puj.proyectoensenarte.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.puj.proyectoensenarte.databinding.ActivityRewardProfileBinding

class RewardProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRewardProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar el binding
        binding = ActivityRewardProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar acción del botón de cerrar
        binding.closeButton.setOnClickListener {
            finish() // Cierra la actividad actual
        }

        // Configurar acción del botón de reclamar recompensa
        binding.buttonContinue.setOnClickListener {
            // Navegar de vuelta a StatisticsProfileActivity
            val intent = Intent(this, StatisticsProfileActivity::class.java)
            startActivity(intent)
            finish() // Cierra la actividad actual para que no se quede en la pila de actividades
        }
    }
}