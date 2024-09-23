package com.puj.proyectoensenarte.profile

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.R
import com.puj.proyectoensenarte.databinding.ActivitySettingsProfileBinding

class SettingsProfileActivity : AppCompatActivity() {
    // Inicializa correctamente el binding
    private lateinit var binding: ActivitySettingsProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Infla el layout con el binding
        binding = ActivitySettingsProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // Configura el listener del botón editar
        binding.botonEditar.setOnClickListener {
            saveUserData()
        }

        binding.backButton.setOnClickListener {
            val intent = Intent(this, ProfileFragmentActivity::class.java)
            startActivity(intent)
        }

        binding.editPhoto.setOnClickListener {
            val avatarSelectionDialog = AvatarSelectionDialogFragment()
            avatarSelectionDialog.show(supportFragmentManager, "AvatarSelectionDialogFragment")
        }


        // Carga los datos del usuario cuando se crea la actividad
        loadUserData()
    }

    // Nueva función para actualizar la imagen de perfil
    fun updateProfileImage(selectedAvatarUrl: String) {
        // Actualiza la imagen de perfil con la URL seleccionada
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid

        if (uid != null) {
            val db = FirebaseFirestore.getInstance()
            val userMap = hashMapOf(
                "photo_url" to selectedAvatarUrl // Actualiza la URL de la foto de perfil
            )

            // Actualizar la URL de la foto de perfil en Firestore
            db.collection("users").document(uid)
                .update(userMap as Map<String, Any>)
                .addOnSuccessListener {
                    Toast.makeText(this, "Foto de perfil actualizada exitosamente.", Toast.LENGTH_SHORT).show()

                    // Actualiza la imagen en la vista de perfil inmediatamente
                    Glide.with(this)
                        .load(selectedAvatarUrl)
                        .placeholder(R.drawable.img_iniciarsesion)
                        .error(R.drawable.img_error)
                        .into(binding.profileImage)
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error actualizando la foto de perfil", e)
                    Toast.makeText(this, "Error al actualizar la foto de perfil.", Toast.LENGTH_SHORT).show()
                }
        }
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
                        val email = document.getString("email")
                        val nickname = document.getString("nickname")
                        val photo = document.getString("photo_url")

                        Glide.with(this)
                            .load(photo) // URL de la imagen
                            .placeholder(R.drawable.placeholder_edit) // Imagen de placeholder mientras se carga
                            .error(R.drawable.placeholder_edit) // Imagen a mostrar en caso de error
                            .into(binding.profileImage)

                        // Muestra los datos en los campos de texto
                        binding.inputName.setText(name)
                        binding.inputUser.setText(nickname)
                    }
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error cargando los datos del usuario", e)
                    Toast.makeText(this, "Error cargando los datos del usuario.", Toast.LENGTH_LONG).show()
                }
        }
    }

    private fun saveUserData() {
        val user = FirebaseAuth.getInstance().currentUser
        val uid = user?.uid

        val name = binding.inputName.text.toString()
        val nickname = binding.inputUser.text.toString()
        val currentPassword = binding.inputLastPassword.text.toString()
        val newPassword = binding.inputNewPassword.text.toString()

        if (uid != null) {
            val db = FirebaseFirestore.getInstance()
            val userMap = hashMapOf(
                "name" to name,
                "nickname" to nickname
            )

            // Verificar que el usuario haya ingresado su contraseña actual
            if (currentPassword.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese su contraseña actual.", Toast.LENGTH_SHORT).show()
                return
            }

            // Reautenticación del usuario antes de actualizar la contraseña
            val credential = EmailAuthProvider.getCredential(user.email!!, currentPassword)
            user.reauthenticate(credential)
                .addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        // Actualizar la contraseña en Firebase Auth (si ha cambiado)
                        if (newPassword.isNotEmpty() && newPassword != currentPassword) {
                            user.updatePassword(newPassword)
                                .addOnCompleteListener { passwordUpdateTask ->
                                    if (passwordUpdateTask.isSuccessful) {
                                        Toast.makeText(this, "Contraseña actualizada en Firebase Auth.", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Log.w(TAG, "Error al actualizar la contraseña en Firebase Auth", passwordUpdateTask.exception)
                                        Toast.makeText(this, "Error al actualizar la contraseña en Firebase Auth.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }

                        // Actualizar datos en Firestore
                        db.collection("users").document(uid)
                            .update(userMap as Map<String, Any>)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Perfil actualizado exitosamente en Firestore.", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error actualizando los datos del usuario en Firestore", e)
                                Toast.makeText(this, "Error al actualizar el perfil en Firestore.", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Log.w(TAG, "Error de reautenticación", authTask.exception)
                        Toast.makeText(this, "Error de reautenticación. Verifique su contraseña actual.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

}