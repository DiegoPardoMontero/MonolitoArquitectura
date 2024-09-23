package com.puj.proyectoensenarte.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.storage.FirebaseStorage
import com.puj.proyectoensenarte.databinding.FragmentAvatarSelectionBinding

class AvatarSelectionDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentAvatarSelectionBinding
    private val storage = FirebaseStorage.getInstance()
    private val avatarUrls = mutableListOf<String>()  // Lista para almacenar las URLs de los avatares

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAvatarSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura el RecyclerView para mostrar los avatares con 2 columnas
        val gridLayoutManager = GridLayoutManager(context, 2) // Número de columnas
        binding.recyclerViewAvatars.layoutManager = gridLayoutManager

        // Configurar el botón Cancelar
        binding.buttonCancel.setOnClickListener {
            dismiss() // Cierra el DialogFragment
        }


        // Cargar los avatares desde Firebase Storage
        loadAvatarsFromStorage()
    }
    private fun loadAvatarsFromStorage() {
        val avatarsRef = storage.reference.child("fotosPerfil/")

        avatarsRef.listAll()
            .addOnSuccessListener { listResult ->
                listResult.items.forEach { item ->
                    item.downloadUrl.addOnSuccessListener { uri ->
                        avatarUrls.add(uri.toString())
                        if (avatarUrls.size == listResult.items.size) {
                            // Solo actualizamos cuando todas las URLs han sido obtenidas
                            updateRecyclerView()
                        }
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error al cargar avatares", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateRecyclerView() {
        val adapter = AvatarAdapter(avatarUrls) { selectedAvatarUrl ->
            (activity as? SettingsProfileActivity)?.updateProfileImage(selectedAvatarUrl)
            dismiss()
        }

        binding.recyclerViewAvatars.adapter = adapter
    }
}