package com.puj.proyectoensenarte

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.puj.proyectoensenarte.databinding.ActivityBottomNavigationBinding
import com.puj.proyectoensenarte.dictionary.DictionaryFragmentActivity
import com.puj.proyectoensenarte.information.InformationFragmentActivity
import com.puj.proyectoensenarte.learning.LearningFragmentActivity
import com.puj.proyectoensenarte.learning.ScrollableMapActivity
import com.puj.proyectoensenarte.profile.ProfileFragmentActivity

class BottomNavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_1 -> {
                    loadFragment(ScrollableMapActivity())
                    true
                }
                R.id.item_2 -> {
                    loadFragment(DictionaryFragmentActivity())
                    true
                }
                R.id.item_3 -> {
                    loadFragment(InformationFragmentActivity())
                    true
                }
                R.id.item_4 -> {
                    loadFragment(ProfileFragmentActivity())
                    true
                }
                else -> false
            }
        }


        // Carga el fragmento por defecto
        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.item_2
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

}
