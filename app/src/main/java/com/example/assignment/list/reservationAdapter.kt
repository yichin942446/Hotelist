package com.example.assignment.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.database.Reservation

class reservationAdapter: RecyclerView.Adapter<reservationAdapter.MyViewHolder>() {

    private var reservationList = emptyList<Reservation>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = reservationList[position]
        holder.itemView.findViewById<TextView>(R.id.txtName).text = currentItem.name
        holder.itemView.findViewById<TextView>(R.id.txtDate).text = currentItem.date
        holder.itemView.findViewById<TextView>(R.id.txtTime).text = currentItem.checkInTime
        holder.itemView.findViewById<TextView>(R.id.txtRoom).text = currentItem.roomList1
    }

    override fun getItemCount(): Int {
        return reservationList.size
    }

    fun setData(reservation: List<Reservation>){
        this.reservationList = reservation
        notifyDataSetChanged()
    }
}