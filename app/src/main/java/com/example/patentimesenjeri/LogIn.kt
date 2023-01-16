package com.example.patentimesenjeri

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.sign

class LogIn : AppCompatActivity() {
    private lateinit var edtEmail : EditText
    private lateinit var edtPassword : EditText
    private lateinit var login : Button
    private lateinit var signUp: Button
    private lateinit var userAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        supportActionBar?.hide()

        edtEmail = findViewById(R.id.Email)
        edtPassword = findViewById(R.id.Password)
        login = findViewById(R.id.LogIn)
        signUp = findViewById(R.id.SignUp)

        userAuth = FirebaseAuth.getInstance()

        signUp.setOnClickListener{
            val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }

        login.setOnClickListener{
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            Login(email, password)
        }
    }

    private fun Login(email: String, password: String) {
        userAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@LogIn, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@LogIn,"Login Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}