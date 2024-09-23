package com.puj.proyectoensenarte

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.puj.proyectoensenarte.authentication.ForgotPasswordActivity
import com.puj.proyectoensenarte.databinding.ActivityIniciarSesionBinding

class IniciarSesionActivity : AppCompatActivity() {
    private lateinit var binding : ActivityIniciarSesionBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIniciarSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.textoCrearCuenta.setOnClickListener{
            val intent = Intent(this, CrearCuentaActivity::class.java)
            startActivity(intent)
        }

        binding.botonIniciarSesion.setOnClickListener(){
            var email : String = binding.textFieldEmail.editText?.text.toString()
            var password : String = binding.textFieldPassword.editText?.text.toString()
            Log.d(TAG, "Email: " + email)
            Log.d(TAG, "Password: " + password)
            signIn(email, password)
        }

        binding.olvidasteContraText.setOnClickListener{
            //Redirect to forgetPassword screen
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

    }

    fun signIn(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Email and Password must not be empty", Toast.LENGTH_SHORT).show()
            return
        }

        if (!isEmailValid(email)) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length < 6) {
            Toast.makeText(this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show()

                    // Redirigir a otra actividad o actualizar la UI
                    val intent = Intent(this, BottomNavigationActivity::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    val exception = task.exception
                    when (exception) {
                        is FirebaseAuthInvalidUserException -> {
                            Toast.makeText(this, "No account found with this email.", Toast.LENGTH_SHORT).show()
                        }
                        is FirebaseAuthInvalidCredentialsException -> {
                            Toast.makeText(this, "Incorrect password.", Toast.LENGTH_SHORT).show()
                        }
                        is FirebaseNetworkException -> {
                            Toast.makeText(this, "Network error. Please try again later.", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(this, "Authentication failed: ${exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
    }

    fun isEmailValid(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
