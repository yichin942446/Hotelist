package com.example.mobileapplication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_registration.*



class RegistrationActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = Firebase.auth
        auth = FirebaseAuth.getInstance()

        nav_to_signIn.setOnClickListener{
            startActivity(Intent(this, SignInActivity2::class.java))
            finish()
        }


        signUp_btn.setOnClickListener{
            signUpUser()
        }

    }

    private fun signUpUser(){
        if(userEmail.text.toString().isEmpty()){
            userEmail.error = "Please enter email"
            userEmail.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail.text.toString()).matches()){
            userEmail.error = "Please enter valid email"
            userEmail.requestFocus()
            return
        }
        if(userPassword.text.toString().isEmpty()){
            userPassword.error = "Please enter password"
            userPassword.requestFocus()
            return
        }

        if(!userPassword.text.toString().equals(confirmPwd.text.toString())){
            confirmPwd.error = "password not correct"
            confirmPwd.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(userEmail.text.toString(), userPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Sign up successfully.",
                            Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, SignInActivity2::class.java))
                    finish()
                } else {
                    Toast.makeText(baseContext, "Sign up failed. Try again after some time",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}