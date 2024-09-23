package com.puj.proyectoensenarte.learning

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.FragmentAndinaLevelBinding

class AndinaLevelFragment : Fragment() {

    private var _binding: FragmentAndinaLevelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAndinaLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura el listener del botón candado
        binding.lock.setOnClickListener {
            Log.d("AndinaLevelFragment", "Candado clickeado para desbloquear el siguiente nivel.")
            // Comunica al Fragmento padre para cambiar al siguiente nivel
            (parentFragment as? LearningFragmentActivity)?.loadFragment(CaribbeanLevelFragment(), R.id.fragmentContainerLevel1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}