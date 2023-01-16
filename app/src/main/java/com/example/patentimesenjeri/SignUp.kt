package com.example.patentimesenjeri

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var edtName: EditText
    private lateinit var edtEmail : EditText
    private lateinit var edtPassword : EditText
    private lateinit var signUp: Button
    private lateinit var userAuth: FirebaseAuth
    private lateinit var userDataBase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        userAuth = FirebaseAuth.getInstance()
        edtName = findViewById(R.id.signupName)
        edtEmail = findViewById(R.id.signupEmail)
        edtPassword = findViewById(R.id.signupPassword)
        signUp = findViewById(R.id.SignUp)

        signUp.setOnClickListener{
            val name = edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            createUser(name,email,password)
        }
    }

    private fun createUser(name: String, email: String, password: String) {
        userAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name,email,userAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp, LogIn::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignUp, "Error", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        userDataBase = FirebaseDatabase.getInstance().getReference()
        userDataBase.child("user").child(uid).setValue(User(name,email,uid))
    }
}