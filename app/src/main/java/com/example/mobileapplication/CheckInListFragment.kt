package com.example.mobileapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobileapplication.adapter.CheckInAdapter
import com.example.mobileapplication.database.CheckInDatabase
import com.example.mobileapplication.databinding.FragmentCheckInListBinding
import com.example.mobileapplication.model.*


class CheckInListFragment : Fragment() {

    private lateinit var binding: FragmentCheckInListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        /*val binding: FragmentCheckInListBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_check_in_list, container, false)*/

        binding = FragmentCheckInListBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        //val arguments = RoomDetailFragmentArgs.fromBundle(arguments!!)

        val dataSource = CheckInDatabase.getInstance(application).checkInDao

        val viewModelFactory = CheckInViewModelFactory(dataSource, application)

        val checkListViewModel = ViewModelProvider(
                this, viewModelFactory).get(CheckInViewModel::class.java)



        binding.viewModel = checkListViewModel



        val adapter = CheckInAdapter()
        binding.checkinList.adapter = adapter

        checkListViewModel.checkIns.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        binding.setLifecycleOwner(this)

        binding.btnHome.setOnClickListener{
            findNavController().navigate(R.id.action_checkInListFragment_to_checkInOptionFragment2)
        }

        return binding.root
    }


}


