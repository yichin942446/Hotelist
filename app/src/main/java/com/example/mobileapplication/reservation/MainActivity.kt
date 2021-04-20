package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileapplication.R
import java.util.*
import android.widget.TextView as TextView1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reservation)

        val calendar: Calendar = Calendar.getInstance()

        val txtdatein = findViewById(R.id.txtdatein) as TextView1
        val txtmonthin = findViewById(R.id.txtmonthin) as TextView1
        val txtyearin = findViewById(R.id.txtyearin) as TextView1
        val txtdateout = findViewById(R.id.txtdateout) as TextView1
        val txtmonthout = findViewById(R.id.txtmonthout) as TextView1
        val txtyearout = findViewById(R.id.txtyearout) as TextView1
        val txtName = findViewById(R.id.txtname) as EditText
        val txtContact = findViewById(R.id.txtcontact) as EditText
        val txtRoom = findViewById(R.id.txtRoom) as EditText
        val txtAdult = findViewById(R.id.txtAdult) as EditText
        val txtChildren = findViewById(R.id.txtChildren) as EditText
        val btndatein = findViewById(R.id.btndatein) as com.google.android.material.button.MaterialButton
        val btndateout = findViewById(R.id.btndateout) as com.google.android.material.button.MaterialButton
        val btnSearch = findViewById(R.id.btnsearch) as com.google.android.material.button.MaterialButton
        val btnClose = findViewById(R.id.btnclose) as ImageButton
        val btnReservationList = findViewById(R.id.btnReservationList) as com.google.android.material.button.MaterialButton

        val date = calendar.get(Calendar.DATE).toString()
        val month = getMonthStr(calendar.get(Calendar.MONTH))
        val year = calendar.get(Calendar.YEAR).toString()

        txtdatein.setText(date)
        txtmonthin.setText(month)
        txtyearin.setText(year)
        txtdateout.setText(date)
        txtmonthout.setText(month)
        txtyearout.setText(year)

        val getIntent = intent
        val getName = getIntent.getStringExtra("name").toString()
        val getContact = getIntent.getStringExtra("contact").toString()
        val getDateIn = getIntent.getStringExtra("dateIn").toString()
        val getMonthIn = getIntent.getStringExtra("monthIn").toString()
        val getYearIn = getIntent.getStringExtra("yearIn").toString()
        val getDateOut = getIntent.getStringExtra("dateOut").toString()
        val getMonthOut = getIntent.getStringExtra("monthOut").toString()
        val getYearOut = getIntent.getStringExtra("yearOut").toString()
        val getCheckin = getIntent.getBooleanExtra("checkin", false)
        val getRoom = getIntent.getStringExtra("room").toString()
        val getAdult = getIntent.getStringExtra("adult").toString()
        val getChildren = getIntent.getStringExtra("children").toString()
        val getCheckTime = getIntent.getStringExtra("checkTime").toString()
        if (getDateIn != "null" && getMonthIn != "null" && getYearIn != "null" && getDateOut != "null" && getMonthOut != "null" && getYearOut != "null") {
            setData(getName, getContact, getDateIn, getMonthIn, getYearIn, getDateOut, getMonthOut, getYearOut, getCheckin, getRoom, getAdult, getChildren)
        }

        btnClose.setOnClickListener {

        }

        txtAdult

        btndatein.setOnClickListener {
            val mName = txtName.text.toString()
            val mContact = txtContact.text.toString()
            val mDateIn = txtdatein.text.toString()
            val mMonthIn = txtmonthin.text.toString()
            val mYearIn = txtyearin.text.toString()
            val mDateOut = txtdateout.text.toString()
            val mMonthOut = txtmonthout.text.toString()
            val mYearOut = txtyearout.text.toString()
            val mRoom = txtRoom.text.toString()
            val mAdult = txtAdult.text.toString()
            val mChildren = txtChildren.text.toString()
            val intent = Intent(this, select_date::class.java).apply {
                putExtra("name", mName);
                putExtra("contact", mContact);
                putExtra("dateIn", mDateIn);
                putExtra("monthIn", mMonthIn);
                putExtra("yearIn", mYearIn);
                putExtra("dateOut", mDateOut);
                putExtra("monthOut", mMonthOut);
                putExtra("yearOut", mYearOut);
                putExtra("room", mRoom);
                putExtra("adult", mAdult);
                putExtra("children", mChildren);
                putExtra("checkin", true);
                putExtra("checkTime", getCheckTime);
            }
            startActivity(intent)
        }

        btndateout.setOnClickListener {
            val mName = txtName.text.toString()
            val mContact = txtContact.text.toString()
            val mDateIn = txtdatein.text.toString()
            val mMonthIn = txtmonthin.text.toString()
            val mYearIn = txtyearin.text.toString()
            val mDateOut = txtdateout.text.toString()
            val mMonthOut = txtmonthout.text.toString()
            val mYearOut = txtyearout.text.toString()
            val mRoom = txtRoom.text.toString()
            val mAdult = txtAdult.text.toString()
            val mChildren = txtChildren.text.toString()
            val intent = Intent(this, select_date::class.java).apply {
                putExtra("name", mName);
                putExtra("contact", mContact);
                putExtra("dateIn", mDateIn);
                putExtra("monthIn", mMonthIn);
                putExtra("yearIn", mYearIn);
                putExtra("dateOut", mDateOut);
                putExtra("monthOut", mMonthOut);
                putExtra("yearOut", mYearOut);
                putExtra("room", mRoom);
                putExtra("adult", mAdult);
                putExtra("children", mChildren);
                putExtra("checkin", false);
                putExtra("checkTime", getCheckTime);
            }
            startActivity(intent)
        }

        btnSearch.setOnClickListener {
            val mName = txtName.text.toString()
            val mContact = txtContact.text.toString()
            val mDateIn = txtdatein.text.toString()
            val mMonthIn = txtmonthin.text.toString()
            val mYearIn = txtyearin.text.toString()
            val mDateOut = txtdateout.text.toString()
            val mMonthOut = txtmonthout.text.toString()
            val mYearOut = txtyearout.text.toString()
            val mRoom = txtRoom.text.toString()
            val mAdult = txtAdult.text.toString()
            val mChildren = txtChildren.text.toString()
            val intent = Intent(this, select_room::class.java).apply {
                putExtra("name", mName);
                putExtra("contact", mContact);
                putExtra("dateIn", mDateIn);
                putExtra("monthIn", mMonthIn);
                putExtra("yearIn", mYearIn);
                putExtra("dateOut", mDateOut);
                putExtra("monthOut", mMonthOut);
                putExtra("yearOut", mYearOut);
                putExtra("room", mRoom);
                putExtra("adult", mAdult);
                putExtra("children", mChildren);
                putExtra("checkTime", getCheckTime);
            }

            if (mName.length == 0)
                txtName.setError("Field Cannot Be Empty")
            else if (mContact.length == 0)
                txtContact.setError("Field Cannot Be Empty")
            else if (mRoom.length == 0)
                txtRoom.setError("Field Cannot Be Empty")
            else if (mAdult.length == 0)
                txtAdult.setError("Field Cannot Be Empty")
            else if (mChildren.length == 0)
                txtChildren.setError("Field Cannot Be Empty")
            else if (mDateIn == mDateOut && mMonthIn == mMonthOut && mYearIn == mYearOut)
                Toast.makeText(this, "Check-In Date & Check-Out Date Cannot Be Same", Toast.LENGTH_SHORT).show()
            else
                startActivity(intent)
        }

        btnReservationList.setOnClickListener{
            val intent = Intent(this, reservation_list::class.java)
            startActivity(intent)
        }
    }

    fun getMonthStr(month: Int): String {
        when (month) {
            0 -> return ("Jan")
            1 -> return ("Feb")
            2 -> return ("Mar")
            3 -> return ("Apr")
            4 -> return ("May")
            5 -> return ("Jun")
            6 -> return ("Jul")
            7 -> return ("Aug")
            8 -> return ("Sep")
            9 -> return ("Oct")
            10 -> return ("Nov")
            else -> return ("Dec")
        }
    }

    fun setData(name: String, contact: String, dateIn: String, monthIn: String, yearIn: String, dateOut: String, monthOut: String, yearOut: String, cin: Boolean, room: String, adult: String, children: String) {
        val txtName = findViewById(R.id.txtname) as EditText
        val txtContact = findViewById(R.id.txtcontact) as EditText
        val txtRoom = findViewById(R.id.txtRoom) as EditText
        val txtAdult = findViewById(R.id.txtAdult) as EditText
        val txtChildren = findViewById(R.id.txtChildren) as EditText
        val txtdatein = findViewById(R.id.txtdatein) as TextView1
        val txtmonthin = findViewById(R.id.txtmonthin) as TextView1
        val txtyearin = findViewById(R.id.txtyearin) as TextView1
        val txtdateout = findViewById(R.id.txtdateout) as TextView1
        val txtmonthout = findViewById(R.id.txtmonthout) as TextView1
        val txtyearout = findViewById(R.id.txtyearout) as TextView1
        txtdatein.setText(dateIn)
        txtmonthin.setText(monthIn)
        txtyearin.setText(yearIn)
        txtdateout.setText(dateOut)
        txtmonthout.setText(monthOut)
        txtyearout.setText(yearOut)

        if (name == "null" || name == "") {
            txtName.setText("")
            txtName.setHint("name")
        } else
            txtName.setText(name)

        if (contact == "null" || contact == "") {
            txtContact.setText("")
            txtContact.setHint("contact number")
        } else
            txtContact.setText(contact)

        if (room == "null" || room == "")
            txtRoom.setText("2")
        else
            txtRoom.setText(room)

        if (adult == "null" || adult == "")
            txtAdult.setText("2")
        else
            txtAdult.setText(adult)

        if (children == "null" || children == "")
            txtChildren.setText("2")
        else
            txtChildren.setText(children)
    }
}
