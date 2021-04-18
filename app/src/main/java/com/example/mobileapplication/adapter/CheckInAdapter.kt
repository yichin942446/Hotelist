package com.example.mobileapplication.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapplication.CheckInListFragmentDirections
import com.example.mobileapplication.R
import com.example.mobileapplication.database.CheckIn
import kotlinx.android.synthetic.main.check_in_list.view.*
import kotlinx.android.synthetic.main.check_in_list.view.room_no
import kotlinx.android.synthetic.main.check_in_list.view.room_type
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

//class CheckInAdapter: ListAdapter<CheckIn, CheckInAdapter.ViewHolder>(CheckInDiffCallback()) {

class CheckInAdapter: RecyclerView.Adapter<CheckInAdapter.ViewHolder>() {
    var period = ""

    var data = listOf<CheckIn>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //return ViewHolder.from(parent)
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.check_in_list,parent,false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val item = getItem(position)
        //holder.bind(item)

        val item = data[position]

        val checkInDate = item.checkInDate.toString()
        val checkOutDate = item.checkOutDate.toString()

        if(checkInDate != "" && checkOutDate != "") {
            val from = LocalDate.parse(checkInDate, DateTimeFormatter.ofPattern("d-M-yyyy"))
            val to = LocalDate.parse(checkOutDate, DateTimeFormatter.ofPattern("d-M-yyyy"))
            period = Period.between(from, to).getDays().toString()
        }



        val date = checkInDate + " > " + checkOutDate + " ( " + period + " day(s) )"

        val adult = item.adultNo
        val child = item.childNo
        val guestNo = adult + child

        val roomNo = "#" + item.roomNo.toString()

        holder.itemView.date.text = date
        holder.itemView.room_type.text = item.roomType.toString()
        holder.itemView.room_no.text = roomNo
        holder.itemView.guest_no.text = guestNo.toString()
        holder.itemView.guest_name.text = item.guestName.toString()
        holder.itemView.rowLayout.setOnClickListener{
            val action = CheckInListFragmentDirections.actionCheckInListFragmentToUpdateFragment(item)
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }



    /*class ViewHolder private constructor(val binding: CheckInListBinding) : RecyclerView.ViewHolder(binding.root) {
        val guestName: TextView = binding.guestName
        val roomType: TextView = binding.roomType
        val guestNo: TextView = binding.guestNo
        val date: TextView = binding.date
        val roomNo: TextView = binding.roomNo

        fun bind(item: CheckIn) {
            binding.checkIn = item
            binding.executePendingBindings()
            //guestName.text = item.guestName.Text.toString()


        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CheckInListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }

    //override fun getItemCount(): Int {
    //    return data.size
    //}*/

    /*class CheckInDiffCallback : DiffUtil.ItemCallback<CheckIn>() {

        override fun areItemsTheSame(oldItem: CheckIn, newItem: CheckIn): Boolean {
            return oldItem.checkInId == newItem.checkInId
        }


        override fun areContentsTheSame(oldItem: CheckIn, newItem: CheckIn): Boolean {
            return oldItem == newItem
        }
    }*/
}