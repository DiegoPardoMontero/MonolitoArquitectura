package com.puj.proyectoensenarte.learning

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityLearningFragmentBinding

class LearningFragmentActivity : Fragment() {

    private var _binding: ActivityLearningFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityLearningFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Cargar el primer nivel por defecto
        loadFragment(AndinaLevelFragment(), binding.fragmentContainerLevel1.id)
    }

    fun loadFragment(fragment: Fragment, containerId: Int) {
        Log.d("LearningFragmentActivity", "Intentando cargar el fragmento: ${fragment::class.java.simpleName}")

        childFragmentManager.beginTransaction().apply {
            setCustomAnimations(
                R.anim.slide_in_up,    // Animación de entrada (hacia arriba)
                R.anim.slide_out_down, // Animación de salida (hacia abajo)
                R.anim.slide_in_up,    // Animación de retorno al volver (hacia arriba)
                R.anim.slide_out_down  // Animación de salida al volver (hacia abajo)
            )

            replace(containerId, fragment)
            addToBackStack(null) // Permite la navegación hacia atrás
            commit()
        }.also {
            Log.d("LearningFragmentActivity", "Transacción de fragmento iniciada correctamente.")
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}