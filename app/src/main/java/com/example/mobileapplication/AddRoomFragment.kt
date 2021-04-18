package com.example.mobileapplication

import android.R
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobileapplication.database.CheckInDatabase
import com.example.mobileapplication.databinding.FragmentAddRoomBinding
import com.example.mobileapplication.model.CheckInViewModel
import com.example.mobileapplication.model.CheckInViewModelFactory

class AddRoomFragment : Fragment() {

    private lateinit var binding: FragmentAddRoomBinding
    private lateinit var checkInViewModel: CheckInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddRoomBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = CheckInDatabase.getInstance(application).checkInDao

        val viewModelFactory = CheckInViewModelFactory(dataSource, application)

        checkInViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(CheckInViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel = checkInViewModel

        binding.btnAddRoom.setOnClickListener{
            addRoom()

        }

        binding.btnViewRoomList.setOnClickListener{
            findNavController().navigate(AddRoomFragmentDirections.actionAddRoomFragmentToRoomListFragment())
        }

        binding.btnHome.setOnClickListener{
            startActivity(Intent(requireContext(), HomeActivity::class.java))
        }

        return binding.root
    }

    private fun addRoom() {

        var pattern = Regex("[R][0-9]{3,4}")

        if (binding.addRoomNo.text.toString().isEmpty()) {
            binding.addRoomNo.error = "Please enter room no"
            binding.addRoomNo.requestFocus()
            return
        }

        if (!pattern.containsMatchIn(binding.addRoomNo.text.toString())) {
            binding.addRoomNo.error = "Please follow format Rxxx"
            binding.addRoomNo.requestFocus()
            return
        }

        findNavController().navigate(AddRoomFragmentDirections.actionAddRoomFragmentToRoomListFragment())

        checkInViewModel.addHotelRoom(this)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        selectRoomType()
    }

    private fun selectRoomType() {

        val items = listOf("Regular", "Standard", "Luxury", "President")
        binding.addRoomType.adapter = ArrayAdapter<String>(requireContext(), R.layout.simple_list_item_1, items)

        binding.addRoomType.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                //binding.txtRoomType.text = items.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //binding.txtRoomType.text = "Please select an option"
            }
        }
    }


}