package com.puj.proyectoensenarte.profile

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivityProfileFragmentBinding

class ProfileFragmentActivity : Fragment() {
    private var binding: ActivityProfileFragmentBinding? = null
    private var adapter: MyAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityProfileFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        loadUserData()
        // Data to populate the RecyclerView with
        val data = ArrayList<String>()
        data.add("Configuraciones de la cuenta")
        data.add("Estadísticas de aprendizaje")
        data.add("Cerrar Sesión")

        // Set up the RecyclerView
        val layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.layoutManager = layoutManager
        adapter = MyAdapter(context, data)
        binding?.recyclerView?.adapter = adapter

        // Add divider
        val dividerItemDecoration = DividerItemDecoration(binding?.recyclerView?.context, layoutManager.orientation)
        binding?.recyclerView?.addItemDecoration(dividerItemDecoration)
    }


    private fun loadUserData() {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid

        if (uid != null) {
            val db = FirebaseFirestore.getInstance()
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val name = document.getString("name")
                        val nickname = document.getString("nickname")
                        val photo = document.getString("photo_url")

                        // Muestra los datos en los campos de texto
                        binding?.nameProfile?.text = name
                        binding?.usernameProfile?.text = nickname

                        // Cargar la imagen de perfil usando Glide
                        Glide.with(this)
                            .load(photo) // URL de la imagen
                            .placeholder(R.drawable.img_placeholder) // Imagen de placeholder mientras se carga
                            .error(R.drawable.img_placeholder) // Imagen a mostrar en caso de error
                            .into(binding?.imageProfile!!)
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error cargando los datos del usuario", e)
                    Toast.makeText(context, "Error cargando los datos del usuario.", Toast.LENGTH_LONG).show()
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
