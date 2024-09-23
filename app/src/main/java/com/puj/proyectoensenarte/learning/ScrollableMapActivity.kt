package com.puj.proyectoensenarte.learning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.puj.proyectoensenarte.databinding.ActivityScrollableMapBinding

class ScrollableMapActivity : Fragment() {

    private var _binding: ActivityScrollableMapBinding? = null
    private val binding get() = _binding!!
    private var isCaribbeanBannerShown = false
    private var isAmazonasBannerShown = false
    private var isFadeOutInProgress = false // Flag para controlar la animación del banner Caribe
    private var isFadeOutInProgressAmazonas = false // Flag para controlar la animación del banner Amazonas

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityScrollableMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Listener de scroll para detectar la posición
        binding.scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            // Verifica si se alcanzó el nivel caribe y no se ha mostrado el banner aún
            if (scrollY >= binding.mapImageCaribbean.top && !isCaribbeanBannerShown) {
                showCaribbeanBanner()
            }

            // Verifica si el scroll ha regresado al nivel Andina y no se está ejecutando fade out
            if (scrollY < binding.level2.top && isCaribbeanBannerShown && !isFadeOutInProgress) {
                fadeOutCaribbeanBanner()
            }

            // Verifica si se alcanzó el nivel Amazonas y no se ha mostrado el banner aún
            if (scrollY >= binding.level1Amazonas.top && !isAmazonasBannerShown) {
                showAmazonasBanner()
            }

            // Verifica si el scroll ha regresado a niveles anteriores y no se está ejecutando fade out
            if (scrollY < binding.level2Caribbean.top && isAmazonasBannerShown && !isFadeOutInProgressAmazonas) {
                fadeOutAmazonasBanner()
            }
        }

        // Configura los clics para los niveles
        setUpClickLevel()
    }

    // Función para ocultar el banner del Caribe con animación
    private fun fadeOutCaribbeanBanner() {
        isFadeOutInProgress = true // Marcamos que la animación está en progreso

        binding.bannerCaribbean.apply {
            animate()
                .alpha(0f) // Desvanecer a 0 (invisible)
                .setDuration(600) // Duración de la animación
                .withEndAction {
                    visibility = View.GONE // Ocultar completamente el banner
                    alpha = 1f // Restaurar alpha a 1 para futuras animaciones
                    isFadeOutInProgress = false // La animación ha terminado
                    isCaribbeanBannerShown = false // El banner ya no está mostrado
                }
                .start()
        }
    }

    // Función para ocultar el banner del Amazonas con animación
    private fun fadeOutAmazonasBanner() {
        isFadeOutInProgressAmazonas = true // Marcamos que la animación está en progreso

        binding.bannerAmazonas.apply {
            animate()
                .alpha(0f) // Desvanecer a 0 (invisible)
                .setDuration(600) // Duración de la animación
                .withEndAction {
                    visibility = View.GONE // Ocultar completamente el banner
                    alpha = 1f // Restaurar alpha a 1 para futuras animaciones
                    isFadeOutInProgressAmazonas = false // La animación ha terminado
                    isAmazonasBannerShown = false // El banner ya no está mostrado
                }
                .start()
        }
    }

    // Función para mostrar el banner del Caribe con animación
    private fun showCaribbeanBanner() {
        binding.bannerCaribbean.apply {
            visibility = View.VISIBLE // Mostrar el banner
            alpha = 0f // Empezar invisible
            animate().alpha(1f).setDuration(900).start() // Desvanecer a visible
        }
        isCaribbeanBannerShown = true // Actualizar el estado del banner
    }

    // Función para mostrar el banner del Amazonas con animación
    private fun showAmazonasBanner() {
        binding.bannerAmazonas.apply {
            visibility = View.VISIBLE // Mostrar el banner
            alpha = 0f // Empezar invisible
            animate().alpha(1f).setDuration(900).start() // Desvanecer a visible
        }
        isAmazonasBannerShown = true // Actualizar el estado del banner
    }

    // Configura los clicks de los niveles
    private fun setUpClickLevel() {
        // Niveles Región Andina
        binding.level1.setOnClickListener {
            Toast.makeText(context, "Nivel 1: Lección de la Región Andina", Toast.LENGTH_SHORT).show()
        }

        binding.level2.setOnClickListener {
            Toast.makeText(context, "Nivel 2: Lección de la Región Andina", Toast.LENGTH_SHORT).show()
        }

        binding.level3.setOnClickListener {
            Toast.makeText(context, "Nivel 3: Lección de la Región Andina", Toast.LENGTH_SHORT).show()
        }

        binding.lockAndina.setOnClickListener {
            Toast.makeText(context, "Nivel bloqueado en Andina", Toast.LENGTH_SHORT).show()
        }

        // Niveles Región Caribe
        binding.level1Caribbean.setOnClickListener {
            Toast.makeText(context, "Nivel 1: Lección de la Región Caribe", Toast.LENGTH_SHORT).show()
        }

        binding.level2Caribbean.setOnClickListener {
            Toast.makeText(context, "Nivel 2: Lección de la Región Caribe", Toast.LENGTH_SHORT).show()
        }

        binding.level3Caribbean.setOnClickListener {
            Toast.makeText(context, "Nivel 3: Lección de la Región Caribe", Toast.LENGTH_SHORT).show()
        }

        binding.lockCaribbean.setOnClickListener {
            Toast.makeText(context, "Nivel bloqueado en Caribe", Toast.LENGTH_SHORT).show()
        }

        // Niveles Región Amazónica
        binding.level1Amazonas.setOnClickListener {
            Toast.makeText(context, "Nivel 1: Lección de la Región Amazónica", Toast.LENGTH_SHORT).show()
        }

        binding.level2Amazonas.setOnClickListener {
            Toast.makeText(context, "Nivel 2: Lección de la Región Amazónica", Toast.LENGTH_SHORT).show()
        }

        binding.level3Amazonas.setOnClickListener {
            Toast.makeText(context, "Nivel 3: Lección de la Región Amazónica", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}