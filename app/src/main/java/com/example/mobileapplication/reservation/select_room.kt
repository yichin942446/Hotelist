package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileapplication.R
import com.google.android.material.chip.Chip

class select_room : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_room)

        //Intent
        val getIntent = intent
        val getName = getIntent.getStringExtra("name").toString()
        val getContact = getIntent.getStringExtra("contact").toString()
        val getDateIn = getIntent.getStringExtra("dateIn").toString()
        val getMonthIn = getIntent.getStringExtra("monthIn").toString()
        val getYearIn = getIntent.getStringExtra("yearIn").toString()
        val getDateOut = getIntent.getStringExtra("dateOut").toString()
        val getMonthOut = getIntent.getStringExtra("monthOut").toString()
        val getYearOut = getIntent.getStringExtra("yearOut").toString()
        val getRoom = getIntent.getStringExtra("room").toString()
        val getAdult = getIntent.getStringExtra("adult").toString()
        val getChildren = getIntent.getStringExtra("children").toString()
        val getCheckTime = getIntent.getStringExtra("checkTime").toString()
        setData(getName, getContact, getDateIn, getMonthIn, getYearIn, getDateOut, getMonthOut, getYearOut, getRoom, getAdult, getChildren, getCheckTime)

        //Text
        val txtCheckTime = findViewById(R.id.txtCheckTime) as EditText

        //Button
        val btnClose = findViewById(R.id.btnclose) as ImageButton
        val btnNext = findViewById(R.id.btnNext) as com.google.android.material.button.MaterialButton

        //Chip
        val chipGroupRoom1 = findViewById(R.id.chipGroupRoom1) as com.google.android.material.chip.ChipGroup
        val chipGroupRoom2 = findViewById(R.id.chipGroupRoom2) as com.google.android.material.chip.ChipGroup
        val chipGroupRoom3 = findViewById(R.id.chipGroupRoom3) as com.google.android.material.chip.ChipGroup

        btnClose.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("name", getName);
                putExtra("contact", getContact);
                putExtra("dateIn", getDateIn);
                putExtra("monthIn", getMonthIn);
                putExtra("yearIn", getYearIn);
                putExtra("dateOut", getDateOut);
                putExtra("monthOut", getMonthOut);
                putExtra("yearOut", getYearOut);
                putExtra("room", getRoom);
                putExtra("adult", getAdult);
                putExtra("children", getChildren);
                putExtra("checkTime", txtCheckTime.text.toString());
            }
            startActivity(intent)
        }

        btnNext.setOnClickListener {
            val roomList1 = ArrayList<String>()
            val roomList2 = ArrayList<String>()
            val roomList3 = ArrayList<String>()

            chipGroupRoom1.checkedChipIds.forEach {
                val chip = findViewById<Chip>(it)
                roomList1.add(chip.text.toString())
            }

            chipGroupRoom2.checkedChipIds.forEach {
                val chip = findViewById<Chip>(it)
                roomList2.add(chip.text.toString())
            }

            chipGroupRoom3.checkedChipIds.forEach {
                val chip = findViewById<Chip>(it)
                roomList3.add(chip.text.toString())
            }


            val intent = Intent(this, reservation_details::class.java).apply {
                putExtra("name", getName);
                putExtra("contact", getContact);
                putExtra("dateIn", getDateIn);
                putExtra("monthIn", getMonthIn);
                putExtra("yearIn", getYearIn);
                putExtra("dateOut", getDateOut);
                putExtra("monthOut", getMonthOut);
                putExtra("yearOut", getYearOut);
                putExtra("room", getRoom);
                putExtra("adult", getAdult);
                putExtra("children", getChildren);
                putExtra("checkTime", txtCheckTime.text.toString());
                putStringArrayListExtra("roomList1", (ArrayList<String>(roomList1)));
                putStringArrayListExtra("roomList2", (ArrayList<String>(roomList2)));
                putStringArrayListExtra("roomList3", (ArrayList<String>(roomList3)));
            }

            if (txtCheckTime.text.toString().length == 0)
                txtCheckTime.setError("Field Cannot Be Empty")
            else if (roomList1.size + roomList2.size + roomList3.size != getRoom.toInt())
                Toast.makeText(this, "Please Choose " + getRoom + " Rooms", Toast.LENGTH_SHORT).show()
            else
                startActivity(intent)
        }
    }

    fun setData(name: String, contact: String, dateIn: String, monthIn: String, yearIn: String, dateOut: String,
                monthOut: String, yearOut: String, room: String, adult: String, children: String, checkTime: String) {
        val txtDetails = findViewById(R.id.txtDetails) as TextView
        val txtCheckTime = findViewById(R.id.txtCheckTime) as EditText

        txtDetails.setText(name + "\n" +
                contact + "\n" +
                dateIn + monthIn + yearIn + " - " + dateOut + monthOut + yearOut + "\n" +
                room + " Rooms " + adult + " Adults " + children + " Children")

        if (checkTime == "null" || checkTime == "") {
            txtCheckTime.setText("")
            txtCheckTime.setHint("Check-in time (12:30PM - 5:00PM)")
        } else
            txtCheckTime.setText(checkTime)
    }

    fun getMonthInt(month: String): Int {
        when (month) {
            "Jan" -> return (0)
            "Feb" -> return (1)
            "Mar" -> return (2)
            "Apr" -> return (3)
            "May" -> return (4)
            "Jun" -> return (5)
            "Jul" -> return (6)
            "Aug" -> return (7)
            "Sep" -> return (8)
            "Oct" -> return (9)
            "Nov" -> return (10)
            else -> return (11)
        }
    }
}