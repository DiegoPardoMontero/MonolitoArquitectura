package com.puj.proyectoensenarte.information

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.FragmentHistoryBinding

class History : Fragment() {
    private lateinit var binding: FragmentHistoryBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater,container, false)
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

        val contentContainer : LinearLayout = binding.contentContainer
        val image1 = createImageView(R.drawable.img_history_1)
        val image2 = createImageView(R.drawable.img_history_2)

        contentContainer.addView(createTitle(getString(R.string.history_titulo2)))
        contentContainer.addView(createSubTitle(getString(R.string.history_subtitulo1)))
        contentContainer.addView(createParagraph(getString(R.string.history_parrafo1)))
        contentContainer.addView(image1)
        contentContainer.addView(createSubTitle(getString(R.string.history_subtitulo2)))
        contentContainer.addView(createParagraph(getString(R.string.history_parrafo2)))
        contentContainer.addView(image2)
        contentContainer.addView(createTitle(getString(R.string.history_titulo3)))
        contentContainer.addView(createSubTitle(getString(R.string.history_subtitulo3)))
        contentContainer.addView(createParagraph(getString(R.string.history_parrafo3)))

    }

    fun createTitle(text: String): TextView {
        val typeface = ResourcesCompat.getFont(requireContext(), R.font.regularbold)
        return TextView(requireContext()).apply {
            this.text = text
            textSize = 36f
            setTypeface(typeface)
            setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 12, 0, 12)  // Margen superior e inferior
            }
        }
    }

    fun createSubTitle(text: String): TextView {
        val typeface = ResourcesCompat.getFont(requireContext(), R.font.regular)
        return TextView(requireContext()).apply {
            this.text = text
            textSize = 28f
            setTypeface(typeface, Typeface.ITALIC)
            setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 8, 0, 8)  // Margen superior e inferior
            }
        }
    }

    fun createParagraph(text: String): TextView {
        val typeface = ResourcesCompat.getFont(requireContext(), R.font.regular)
        return TextView(requireContext()).apply {
            this.text = text
            textSize = 20f
            setTypeface(typeface)
            setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 8, 0, 8)  // Margen superior e inferior
            }
        }
    }

    fun createImageView(imageResId: Int): ImageView {
        val roundedDrawable = ResourcesCompat.getDrawable(
            resources,
            R.drawable.rounded_image_background,
            null
        )

        val imageView = ImageView(requireContext()).apply {
            setImageResource(imageResId)  // Establecer la imagen desde un recurso
            scaleType = ImageView.ScaleType.CENTER_CROP  // Ajustar el tipo de escala
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                200.dpToPx()  // Limitar la altura a 300dp
            ).apply {
                setMargins(0, 30, 0, 30)  // Márgenes
            }
            clipToOutline = true  // Permitir que la imagen se recorte según el contorno
            background = roundedDrawable
        }
        return imageView
    }

    fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()
}