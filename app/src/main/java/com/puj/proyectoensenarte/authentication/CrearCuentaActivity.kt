package com.puj.proyectoensenarte

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.databinding.ActivityCrearCuentaBinding
import com.puj.proyectoensenarte.onboarding.SliderActivity

class CrearCuentaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCrearCuentaBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearCuentaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.textoIniciarSesion.setOnClickListener{
            val intent = Intent(this, IniciarSesionActivity::class.java)
            startActivity(intent)
        }
        binding.botonCrearCuenta.setOnClickListener{
            var name : String = binding.textFieldNombre.editText?.text.toString()
            var email : String = binding.textFieldEmail.editText?.text.toString()
            var password : String = binding.textFieldPassword.editText?.text.toString()
            var nickname : String = binding.textFieldUsuario.editText?.text.toString()
            // Validación de los campos antes de crear la cuenta
            if (validateInput(name, email, password, nickname)) {
                createAccount(name, email, password, nickname)
            }
        }
    }

    private fun validateInput(name: String, email: String, password: String, nickname: String): Boolean {
        if (name.isBlank()) {
            Toast.makeText(this, "Por favor ingresa tu nombre", Toast.LENGTH_SHORT).show()
            return false
        }
        if (email.isBlank()) {
            Toast.makeText(this, "Por favor ingresa tu correo electrónico", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Por favor ingresa un correo válido", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isBlank() || password.length < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
            return false
        }
        if (nickname.isBlank()) {
            Toast.makeText(this, "Por favor ingresa tu nombre de usuario", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun createAccount(name: String, email: String, password: String, nickname: String) {
        val photoUrl = "https://firebasestorage.googleapis.com/v0/b/proyectoensenarte-d4dd2.appspot.com/o/fotosPerfil%2Favatar_capybara.png?alt=media&token=67df92b2-af74-4386-ae09-dd1b8cfc40d8"
        val streakDays: Number = 0
        val xpPoints: Number = 0
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, get the user's information
                    val user = auth.currentUser
                    user?.let {
                        val uid = user.uid

                        // Crear un nuevo usuario con el UID
                        val userMap = hashMapOf(
                            "email" to email,
                            "uid" to uid,
                            "name" to name,
                            "nickname" to nickname,
                            "photo_url" to photoUrl,
                            "streakDays" to streakDays,
                            "xpPoints" to xpPoints
                        )

                        // Guardar datos del usuario en Firestore
                        db.collection("users").document(uid)
                            .set(userMap)
                            .addOnSuccessListener {
                                Log.d(TAG, "User data stored successfully")
                                Toast.makeText(baseContext, "Registrado exitosamente!.", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, BottomNavigationActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error storing user data", e)
                            }
                    }
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "No se pudo registrar: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
