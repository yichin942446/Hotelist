package com.example.mobileapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapplication.CheckInListFragmentDirections
import com.example.mobileapplication.R
import com.example.mobileapplication.RoomListFragment
import com.example.mobileapplication.RoomListFragmentDirections
import com.example.mobileapplication.database.HotelRoom
import kotlinx.android.synthetic.main.check_in_list.view.*
import kotlinx.android.synthetic.main.hotel_room_list.view.*

class HotelRoomAdapter: RecyclerView.Adapter<HotelRoomAdapter.ViewHolder>() {

    var data = listOf<HotelRoom>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.hotel_room_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.itemView.roomType.text = item.addRoomType.toString()
        holder.itemView.roomNo.text = item.addRoomNo.toString()
        holder.itemView.gridLayout.setOnClickListener{
            val action = RoomListFragmentDirections.actionRoomListFragment2ToViewRoomFragment(item)
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}