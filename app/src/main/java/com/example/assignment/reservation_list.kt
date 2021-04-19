package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment.database.ReservationViewModel
import com.example.assignment.databinding.ActivityReservationListBinding

class reservation_list : AppCompatActivity() {

    private lateinit var binding: ActivityReservationListBinding
    private lateinit var reservationViewModel: ReservationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_list)
        val btn = findViewById(R.id.btn) as com.google.android.material.button.MaterialButton
        val btnClose = findViewById(R.id.btnclose) as ImageButton

        var roomList1 = ArrayList<String>()
        var roomList2 = ArrayList<String>()
        var roomList3 = ArrayList<String>()

        roomList1.add("101")
        roomList1.add("102")
        roomList2.add("201")
        roomList3.add("301")

        btn.setOnClickListener{
            val intent = Intent(this, reservation_details::class.java).apply {
                putExtra("name", "Ooi Yi Chin");
                putExtra("contact", "0123456789");
                putExtra("dateIn", "1");
                putExtra("monthIn", "May");
                putExtra("yearIn", "2021");
                putExtra("dateOut", "6");
                putExtra("monthOut", "May");
                putExtra("yearOut", "2021");
                putExtra("room", "4");
                putExtra("adult", "6");
                putExtra("children", "1");
                putExtra("checkTime", "3:00");
                putStringArrayListExtra("roomList1", (ArrayList<String>(roomList1)));
                putStringArrayListExtra("roomList2", (ArrayList<String>(roomList2)));
                putStringArrayListExtra("roomList3", (ArrayList<String>(roomList3)));
                putExtra("isEdit", true);
            }
            startActivity(intent)
        }

        btnClose.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}