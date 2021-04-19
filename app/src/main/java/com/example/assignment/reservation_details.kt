package com.example.assignment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.assignment.database.ReservationViewModel
import com.example.assignment.database.reservationDatabase
import com.example.assignment.database.reservationViewModelFactory
import com.example.assignment.databinding.ActivityReservationDetailsBinding
import com.google.android.material.button.MaterialButton
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class reservation_details : AppCompatActivity() {
    private lateinit var mReservationViewModel: ReservationViewModel
    private lateinit var binding: ActivityReservationDetailsBinding

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val application = requireNotNull(this).application
        val dataSource = reservationDatabase.getDatabase(application).reservationDao()
        val viewModelFactory = reservationViewModelFactory(dataSource, application)

        mReservationViewModel = ViewModelProvider(this, viewModelFactory).get(ReservationViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel = mReservationViewModel

        val btnClose = findViewById(R.id.btnclose) as ImageButton
        val btnCancel = findViewById(R.id.btnCancel) as MaterialButton
        val btnRemove = findViewById(R.id.btnRemove) as MaterialButton
        val btnConfirm = findViewById(R.id.btnConfirm) as MaterialButton
        //Intent
        val getIntent = intent
        val getName = getIntent.getStringExtra("name") as String
        val getContact = getIntent.getStringExtra("contact") as String
        val getDateIn = getIntent.getStringExtra("dateIn") as String
        val getMonthIn = getIntent.getStringExtra("monthIn") as String
        val getYearIn = getIntent.getStringExtra("yearIn") as String
        val getDateOut = getIntent.getStringExtra("dateOut") as String
        val getMonthOut = getIntent.getStringExtra("monthOut") as String
        val getYearOut = getIntent.getStringExtra("yearOut") as String
        val getRoom = getIntent.getStringExtra("room") as String
        val getAdult = getIntent.getStringExtra("adult") as String
        val getChildren = getIntent.getStringExtra("children") as String
        val getCheckTime = getIntent.getStringExtra("checkTime") as String
        val getRoomList1 = getIntent().getStringArrayListExtra("roomList1") as ArrayList<String>
        val getRoomList2 = getIntent().getStringArrayListExtra("roomList2") as ArrayList<String>
        val getRoomList3 = getIntent().getStringArrayListExtra("roomList3") as ArrayList<String>
        val isEdit = getIntent().getBooleanExtra("isEdit", false)

        if (isEdit) {
            btnRemove.visibility = View.VISIBLE
            btnCancel.visibility = View.INVISIBLE
        } else if (!isEdit) {
            btnRemove.visibility = View.INVISIBLE
            btnCancel.visibility = View.VISIBLE
        }

        setData(getName, getContact, getDateIn, getMonthIn, getYearIn, getDateOut, getMonthOut, getYearOut, getRoom, getAdult, getChildren, getCheckTime, getRoomList1, getRoomList2, getRoomList3)
        //ArrayList<String> test = getIntent().getStringArrayListExtra("test")

        btnClose.setOnClickListener {
            val intent = Intent(this, select_room::class.java).apply {
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
                putExtra("checkTime", getCheckTime);
            }
            startActivity(intent)
        }

        btnConfirm.setOnClickListener {
            val intent = Intent(this, reservation_list::class.java).apply {
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
                putExtra("checkTime", getCheckTime);
                putStringArrayListExtra("roomList1", (ArrayList<String>(getRoomList1)));
                putStringArrayListExtra("roomList2", (ArrayList<String>(getRoomList2)));
                putStringArrayListExtra("roomList3", (ArrayList<String>(getRoomList3)));
            }
            Toast.makeText(this, "Reserve Succussful", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        btnCancel.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnRemove.setOnClickListener {
            val builder = AlertDialog.Builder(this@reservation_details)
            builder.setMessage("Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        Toast.makeText(this, "Remove Succussful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    .setNegativeButton("No") { dialog, id ->
                        dialog.dismiss()
                    }
            val alert = builder.create()
            alert.show()
        }
    }

    fun setData(name: String, contact: String, dateIn: String, monthIn: String, yearIn: String, dateOut: String,
                monthOut: String, yearOut: String, room: String, adult: String, children: String, checkTime: String,
                roomList1: ArrayList<String>, roomList2: ArrayList<String>, roomList3: ArrayList<String>) {
        val txtDetails = findViewById(R.id.txtDetails) as TextView
        val txtCost = findViewById(R.id.txtCost) as TextView
        val chipGroupRoomDetails = findViewById(R.id.chipGroupRoomDetails) as com.google.android.material.chip.ChipGroup
        val checkIn = getMonthFullInt(monthIn) + "/" + dateIn + "/" + yearIn
        val checkOut = getMonthFullInt(monthOut) + "/" + dateOut + "/" + yearOut
        val checkInDate: Date
        val checkOutDate: Date
        val dates = SimpleDateFormat("MM/dd/yyyy")
        checkInDate = dates.parse(checkIn)
        checkOutDate = dates.parse(checkOut)
        val difference: Long = abs(checkInDate.time - checkOutDate.time)
        val dayDifference = (difference / (24 * 60 * 60 * 1000)) + 1
        val room1Number = roomList1.size
        val room2Number = roomList2.size
        val room3Number = roomList3.size
        val room1Total = room1Number * 100 * dayDifference
        val room2Total = room2Number * 150 * dayDifference
        val room3Total = room3Number * 200 * dayDifference
        val totalCost = room1Total + room2Total + room3Total
        val roomListTotal = ArrayList<String>()
        roomListTotal.addAll(roomList1)
        roomListTotal.addAll(roomList2)
        roomListTotal.addAll(roomList3)

        txtDetails.setText(name + "\n" +
                contact + "\n" +
                dateIn + monthIn + yearIn + " - " + dateOut + monthOut + yearOut +
                "\nCheck-In Time : " + checkTime + " PM\n" +
                room + " Rooms " + adult + " Adults " + children + " Children")

        if (roomListTotal.size > 0) {
            for (i in 0 until roomListTotal.size) {
                val chip = com.google.android.material.chip.Chip(chipGroupRoomDetails.context)
                chip.setText(roomListTotal[i])
                chip.isClickable = false
                chip.chipBackgroundColor = getColorStateList(R.color.mBlue)
                chipGroupRoomDetails.addView(chip)
            }
            txtCost.setText("RM100 X " + room1Number + " X " + dayDifference + " = RM" + room1Total +
                    "\nRM150 X " + room2Number + " X " + dayDifference + " = RM" + room2Total +
                    "\nRM200 X " + room3Number + " X " + dayDifference + " = RM" + room3Total +
                    "\nTotal = RM" + totalCost)
        }
    }

    fun getRoomList(roomList: ArrayList<String>): String {
        return (roomList.joinToString(","))
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


    fun getMonthFullInt(month: String): String {
        when (month) {
            "Jan" -> return ("01")
            "Feb" -> return ("02")
            "Mar" -> return ("03")
            "Apr" -> return ("04")
            "May" -> return ("05")
            "Jun" -> return ("06")
            "Jul" -> return ("07")
            "Aug" -> return ("08")
            "Sep" -> return ("09")
            "Oct" -> return ("10")
            "Nov" -> return ("11")
            else -> return ("12")
        }
    }
}