package com.puj.proyectoensenarte.information

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.FragmentLinksBinding

class Links : Fragment() {
    private lateinit var binding: FragmentLinksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLinksBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, InformationFragmentActivity()) // Aquí el ID del contenedor donde se muestran los fragmentos
                .addToBackStack(null)  // Esto permite volver al fragmento anterior
                .commit()
        }
        binding.cardviewHETAH.setOnClickListener {
            // Lógica para redirigir a la página de Hetah.net
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://hetah.net"))
            startActivity(intent)
        }

        binding.cardviewEdutin.setOnClickListener {
            // Lógica para redirigir a la página de Edutin Academy
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://edutin.com/lengua-de-senas-colombia"))
            startActivity(intent)
        }

        binding.cardviewMaguare.setOnClickListener {
            // Lógica para redirigir a la página de Maguaré
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://maguare.gov.co/lsc/"))
            startActivity(intent)
        }

        binding.cardviewFunSinapsis.setOnClickListener {
            // Lógica para redirigir a la página de FunSinapsis
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://funsinapsis.com"))
            startActivity(intent)
        }

        binding.cardviewINSOR.setOnClickListener {
            // Lógica para redirigir a la página del INSOR
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://educativo.insor.gov.co"))
            startActivity(intent)
        }

        binding.cardviewFENASCOL.setOnClickListener {
            // Lógica para redirigir a la página de FENASCOL
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://fenascol.org.co"))
            startActivity(intent)
        }

        binding.cardviewINSOREducativo.setOnClickListener {
            // Lógica para redirigir a la página de INSOR Educativo
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://educativo.insor.gov.co"))
            startActivity(intent)
        }

        binding.cardviewINSORDiccionario.setOnClickListener {
            // Lógica para redirigir a la página del Diccionario de LSC
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://educativo.insor.gov.co/diccionario/"))
            startActivity(intent)
        }


    }

}