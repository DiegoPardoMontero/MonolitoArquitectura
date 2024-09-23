package com.puj.proyectoensenarte.learning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.FragmentAmazonasLevelBinding

class AmazonasLevelFragment : Fragment() {

    private var _binding: FragmentAmazonasLevelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAmazonasLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.upLevel3To2.setOnClickListener{
            (parentFragment as? LearningFragmentActivity)?.loadFragment(CaribbeanLevelFragment(), R.id.fragmentContainerLevel1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}