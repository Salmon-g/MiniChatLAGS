package com.example.minichatlags

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.minichatlags.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

//import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val auth = Firebase.auth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginbtn = findViewById<Button>(R.id.loginButton)
        val createbtn = findViewById<Button>(R.id.createButton)

        loginbtn.setOnClickListener { loginUser() }
        createbtn.setOnClickListener { createUser() }

        checkUser()
    }

    private fun checkUser()
    {
        var currentUser = auth.currentUser

        if(currentUser != null)
        {
            val intent = Intent(this, ChatsListActivity::class.java)
            intent.putExtra("user",currentUser.email)
            startActivity(intent)
            finish()
        } else {

        }
    }
    private fun loginUser()
    {
        var email = findViewById<EditText>(R.id.emailText).text.toString()
        val password = findViewById<EditText>(R.id.passwordText).text.toString()

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
            if (task.isSuccessful){
                checkUser()
            } else {
                task.exception?.let{
                    Toast.makeText(baseContext, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
    private fun createUser()
    {
        var email = findViewById<EditText>(R.id.emailText).text.toString()
        val password = findViewById<EditText>(R.id.passwordText).text.toString()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
            if (task.isSuccessful){
                checkUser()
            } else {
                task.exception?.let{
                    Toast.makeText(baseContext, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}