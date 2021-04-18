package com.example.mobileapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_user.*

class UserActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        userID.text = currentUser?.uid
        email.text = currentUser?.email

        btn_home.setOnClickListener{
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

    }



}