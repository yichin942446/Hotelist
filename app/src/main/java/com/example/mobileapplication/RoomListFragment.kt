package com.example.mobileapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mobileapplication.adapter.CheckInAdapter
import com.example.mobileapplication.adapter.HotelRoomAdapter
import com.example.mobileapplication.database.CheckInDatabase
import com.example.mobileapplication.databinding.FragmentRoomListBinding
import com.example.mobileapplication.model.CheckInViewModel
import com.example.mobileapplication.model.CheckInViewModelFactory


class RoomListFragment : Fragment() {

    private lateinit var binding: FragmentRoomListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentRoomListBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        //val arguments = RoomDetailFragmentArgs.fromBundle(arguments!!)

        val dataSource = CheckInDatabase.getInstance(application).checkInDao

        val viewModelFactory = CheckInViewModelFactory(dataSource, application)

        val checkInViewModel = ViewModelProvider(
                this, viewModelFactory).get(CheckInViewModel::class.java)

         //recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val manager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        binding.roomList.layoutManager = manager

        binding.viewModel = checkInViewModel
        val adapter = HotelRoomAdapter()

        binding.roomList.adapter = adapter


        checkInViewModel.hotelRooms.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        binding.setLifecycleOwner(this)


        return binding.root


    }

}