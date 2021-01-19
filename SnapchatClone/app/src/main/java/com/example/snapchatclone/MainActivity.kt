package com.example.snapchatclone

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.example.snapchatclone.SnapsActivity as SnapsActivity

@Suppress("NAME_SHADOWING")
class MainActivity : AppCompatActivity() {

    var emailEditText: EditText? = null
    var passwordEditText: EditText? = null
    private val mAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val goButton: Button = findViewById(R.id.goButton)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        goButton.setOnClickListener {
            //Check in if we can log in the user
            //Move to next Activity
            mAuth.signInWithEmailAndPassword(
                emailEditText?.text.toString(),
                passwordEditText?.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        task.result?.user?.let { it1 ->
                            FirebaseDatabase.getInstance().reference.child("users").child(
                                it1.uid).child("email").setValue(emailEditText?.text.toString())
                        }
                        logIn()
                    } else {
                        // If sign in fails, display a message to the user.
                        mAuth.createUserWithEmailAndPassword(
                            emailEditText?.text.toString(),
                            passwordEditText?.text.toString()
                        ).addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                //Add to database
                                logIn()
                            } else {
                                Toast.makeText(this, "Login failed, try again.", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
        }
    }

    private fun logIn() {
        val intent = Intent(this, SnapsActivity::class.java)
        startActivity(intent)
    }
}