package com.example.mobileapplication

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.mobileapplication.databinding.ActivityHomeBinding
import com.firebase.ui.auth.AuthUI
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)



        //val binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.checkinBtn.setOnClickListener({
            startNextActivity()
        })

        binding.checkoutBtn.setOnClickListener{
            startActivity(Intent(this, ManageRoomActivity::class.java))
            finish()
        }

        //val btn: Button = findViewById(R.id.guest_next_btn)

        //val guestBtn: Button = findViewById(R.id.guest_next_btn)
        //guestBtn.setOnClickListener{startNextActivity()}

    }



    private fun startNextActivity() {
       val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
        finish()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.logout){
            val builder = AlertDialog.Builder(this)

            builder.setPositiveButton("yes"){_,_->
                AuthUI.getInstance().signOut(this)
                startActivity(Intent(this, SignInActivity::class.java))
                finish()
            }
            builder.setNegativeButton("no"){_,_-> }
            builder.setTitle("Logout?")
            builder.setMessage("Are you sure you want to logout?")
            builder.create().show()

        }else if(item.itemId == R.id.aboutYou){
            startActivity(Intent(this, UserActivity::class.java))
            finish()
        }


        return super.onOptionsItemSelected(item)
    }

}