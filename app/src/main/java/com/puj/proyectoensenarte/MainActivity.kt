package com.puj.proyectoensenarte

import android.content.Intent
import android.net.Uri
import android.os.Bundle

import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.puj.proyectoensenarte.databinding.ActivityMainBinding
import com.puj.proyectoensenarte.onboarding.SliderActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding // Se una instancia de la clase ActivityMainBinding que genera automáticamente Android al utilizar BindingView

    override fun onCreate(savedInstanceState: Bundle?) { // Bundle que contiene el estado previamente guardado de la actividad, si es que hay alguno.
        super.onCreate(savedInstanceState) // asegurarse de que la actividad se inicialice correctamente, incluyendo la creación de la interfaz de usuario y la configuración inicial
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding =
            ActivityMainBinding.inflate(layoutInflater) // infla el XML y crea una instancia de ActivityMainBinding con todas las referencias de las vistas
        setContentView(binding.root) // vista raíz del xml referenciado

        // Configura el VideoView
        val videoView = binding.videoView
        val videoUri =
            Uri.parse("android.resource://" + packageName + "/" + R.raw.splash5s) // Asegúrate de que el video esté en la carpeta 'res/raw'
        videoView.setVideoURI(videoUri)

        videoView.start()

        videoView.setOnCompletionListener {
            val intent = Intent(this, SliderActivity::class.java)
            startActivity(intent)
        }

    }
}