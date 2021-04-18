package com.example.mobileapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.activity_sign_in2.*

class SignInActivity2 : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in2)

        auth = FirebaseAuth.getInstance()

        signIn_btn2.setOnClickListener{
            login()
        }

        nav_to_signUp2.setOnClickListener{
            startActivity(Intent(this, RegistrationActivity::class.java))
            finish()
        }

    }

    private fun login() {
        if(log_userEmail.text.toString().isEmpty()){
            log_userEmail.error = "Please enter email"
            log_userEmail.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(log_userEmail.text.toString()).matches()){
            log_userEmail.error = "Please enter valid email"
            log_userEmail.requestFocus()
            return
        }
        if(log_userPassword.text.toString().isEmpty()){
            log_userPassword.error = "Please enter password"
            log_userPassword.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(log_userEmail.text.toString(), log_userPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {

                    updateUI(null)
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser != null){
            startActivity(Intent(this, HomeActivity::class.java))
        }else{
            Toast.makeText(baseContext, "Log in failed.",
                Toast.LENGTH_SHORT).show()
        }
    }


}