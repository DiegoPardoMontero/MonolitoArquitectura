package com.puj.proyectoensenarte.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.puj.proyectoensenarte.IniciarSesionActivity
import com.puj.proyectoensenarte.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding : ActivityForgotPasswordBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.botonEnviarCorreoRecuperacion.setOnClickListener(){
            val email : String = binding.textFieldEmailRecuperacion.editText?.text.toString()
            sendPasswordReset(email)
        }

        binding.volverAIniciarSesion.setOnClickListener(){
            val intent = Intent(this, IniciarSesionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun sendPasswordReset(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Password reset email sent to $email", Toast.LENGTH_SHORT).show()
                } else {
                    val exception = task.exception
                    when (exception) {
                        is FirebaseAuthInvalidUserException -> {
                            Toast.makeText(this, "No account found with this email.", Toast.LENGTH_SHORT).show()
                        }
                        is FirebaseNetworkException -> {
                            Toast.makeText(this, "Network error. Please try again later.", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(this, "Error: ${exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
    }
}