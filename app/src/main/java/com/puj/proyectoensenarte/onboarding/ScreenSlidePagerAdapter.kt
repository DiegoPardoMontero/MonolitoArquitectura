/*
adaptador que se utiliza con ViewPager2 para proporcionar tres fragmentos
distintos, permitiendo a los usuarios deslizarse horizontalmente entre ellos.
 */

package com.puj.proyectoensenarte.onboarding


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter // permite a los usuarios deslizarse entre diferentes vistas o fragmentos de contenido de manera horizontal


class ScreenSlidePagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 3 // 3 fragmentos en el view pager

    override fun createFragment(position: Int): Fragment {
        return when (position) { // crea y devuelve un fragmento basado en la posición proporcionada.
            0 -> OnboardingOneActivity()
            1 -> OnboardingTwoActivity() // Si la posición es 1, devuelve una instancia de SecondFragmentActivity.
            else -> OnboardingThreeActivity()
        }
    }
}
