package com.puj.proyectoensenarte.onboarding


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.puj.proyectoensenarte.BottomNavigationActivity
import com.puj.proyectoensenarte.CrearCuentaActivity
import com.puj.proyectoensenarte.IniciarSesionActivity
import com.puj.proyectoensenarte.databinding.ActivityOnboardingOneBinding

class OnboardingOneActivity : Fragment() {
    private var _binding: ActivityOnboardingOneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityOnboardingOneBinding.inflate(inflater, container, false)

        binding.botonCrearCuenta1.setOnClickListener {
            val intent = Intent(activity, CrearCuentaActivity::class.java)
            startActivity(intent)
        }

        binding.botonIniciarSesion1.setOnClickListener {
            val intent = Intent(activity, IniciarSesionActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
