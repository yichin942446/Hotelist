package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class select_date : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_date)

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
        val calendar = Calendar.getInstance()

        val calendarView = findViewById(R.id.calendarView) as CalendarView
        val btnClose = findViewById(R.id.btnclose) as ImageButton
        val txtSelectDate = findViewById(R.id.txtSelectDate) as TextView

        val oldDateIn = getDateIn.toInt()
        val oldMonthIn = getMonthInt(getMonthIn)
        val oldYearIn = getYearIn.toInt()
        val oldDateOut = getDateOut.toInt()
        val oldMonthOut = getMonthInt(getMonthOut)
        val oldYearOut = getYearOut.toInt()

        if (getCheckin) {
            calendar.set(oldYearIn, oldMonthIn, oldDateIn)
            txtSelectDate.setText("Check-In Date")
        } else {
            calendar.set(oldYearOut, oldMonthOut, oldDateOut)
            txtSelectDate.setText("Check-Out Date")
        }

        calendarView.setDate(calendar.timeInMillis)

        btnClose.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("name", getName);
                putExtra("contact", getContact);
                putExtra("dateIn", getDateIn);
                putExtra("monthIn", (getMonthIn));
                putExtra("yearIn", getYearIn);
                putExtra("dateOut", getDateOut);
                putExtra("monthOut", (getMonthOut));
                putExtra("yearOut", getYearOut);
                putExtra("room", getRoom);
                putExtra("adult", getAdult);
                putExtra("children", getChildren);
                putExtra("checkTime", getCheckTime);
            }
            startActivity(intent)
        }

        calendarView.setOnDateChangeListener { calendarView, year, month, date ->

            val mDate = date.toString()
            val mMonth = month
            val mYear = year.toString()
            val mDateIn = getDateIn
            val mMonthIn = getMonthIn
            val mYearIn = getYearIn

            if (getCheckin) {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("checkin", true);
                    putExtra("name", getName);
                    putExtra("contact", getContact);
                    putExtra("dateIn", mDate);
                    putExtra("monthIn", getMonthStr(mMonth));
                    putExtra("yearIn", mYear);
                    putExtra("dateOut", mDate);
                    putExtra("monthOut", getMonthStr(mMonth));
                    putExtra("yearOut", mYear);
                    putExtra("room", getRoom);
                    putExtra("adult", getAdult);
                    putExtra("children", getChildren);
                    putExtra("checkTime", getCheckTime);
                }
                startActivity(intent)
            } else {

                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("checkin", false);
                    putExtra("name", getName);
                    putExtra("contact", getContact);
                    putExtra("dateIn", mDateIn);
                    putExtra("monthIn", mMonthIn);
                    putExtra("yearIn", mYearIn);
                    putExtra("dateOut", mDate);
                    putExtra("monthOut", getMonthStr(mMonth));
                    putExtra("yearOut", mYear);
                    putExtra("room", getRoom);
                    putExtra("adult", getAdult);
                    putExtra("children", getChildren);
                    putExtra("checkTime", getCheckTime);
                }
                startActivity(intent)
            }
        }
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
}