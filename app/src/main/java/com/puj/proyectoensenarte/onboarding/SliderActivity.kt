package com.puj.proyectoensenarte.onboarding
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.puj.proyectoensenarte.R

import com.puj.proyectoensenarte.databinding.ActivitySliderBinding



class SliderActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySliderBinding
    private lateinit var indicators: Array<ImageView> // arreglo de tipo ImageView, que a la final son drawables como indicadores

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySliderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ScreenSlidePagerAdapter(this) //instancio el adaptador que permite deslizarnos según la posición
        binding.viewPager.adapter = adapter // al view pager que trae del XML, lo asocio con el adptador instanciado

        setupIndicators() // pone todos los indicadores inactivos en pantalla con su drawable?
        setCurrentIndicator(0)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setCurrentIndicator(position) //Registra un callback para cambiar el indicador cuando se cambia de página.
            }
        })
    }

    private fun setupIndicators() {
        val indicatorLayout = binding.indicatorLayout //instancio mi indicador que tenia en el xml
        indicators = Array(3) { ImageView(this) } // Inicializa el array de indicadores con 3 ImageView.

        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0) // márgenes para los indicadores

        for (i in indicators.indices) { //Itera sobre los índices del array de indicadore
            indicators[i] = ImageView(this) //Crea una nueva ImageView para cada indicador
            indicators[i].setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.indicator_inactive //Establece el drawable inicial como inactivo
                )
            )
            indicators[i].layoutParams = layoutParams //Asocia los parámetros de layout.
            indicators[i].setOnClickListener {
                binding.viewPager.currentItem = i //cambiar de página haciendo click en el drawable
            }
            indicatorLayout.addView(indicators[i]) //Añade el indicador al layout.
        }
    }

    private fun setCurrentIndicator(index: Int) { //Actualiza los indicadores para reflejar la página activa
        for (i in indicators.indices) {
            if (i == index) { //Si el índice coincide con el índice proporcionado
                indicators[i].setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.indicator_active //establece el drawable como activo
                    )
                )
            } else {
                indicators[i].setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.indicator_inactive // sino, establece el drawable como activo
                    )
                )
            }
        }
    }
}
