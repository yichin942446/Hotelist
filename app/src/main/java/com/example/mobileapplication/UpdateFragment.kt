package com.example.mobileapplication

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobileapplication.database.CheckIn
import com.example.mobileapplication.database.CheckInDatabase
import com.example.mobileapplication.databinding.FragmentStayDetailBinding
import com.example.mobileapplication.databinding.FragmentUpdateBinding
import com.example.mobileapplication.model.CheckInViewModel
import com.example.mobileapplication.model.CheckInViewModelFactory
import kotlinx.android.synthetic.main.fragment_update.*


class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var binding: FragmentUpdateBinding
    private lateinit var checkInViewModel: CheckInViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdateBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = CheckInDatabase.getInstance(application).checkInDao

        val viewModelFactory = CheckInViewModelFactory(dataSource, application)

        checkInViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(CheckInViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel = checkInViewModel

        binding.updateGuestName.setText(args.currentCheckIn.guestName)
        binding.updateGuestContact.setText(args.currentCheckIn.guestContact)
        binding.updateGuestEmail.setText(args.currentCheckIn.guestEmail)

        /*checkInViewModel.navigateToUpdate2.observe(viewLifecycleOwner, Observer {currentCheckIn ->
            currentCheckIn?.let {
                this.findNavController().navigate(
                    StayDetailFragmentDirections
                        .actionStayDetailFragment2ToRoomDetailFragment(currentCheckIn.checkInId))

                checkInViewModel.doneNavigating()
            }
        })*/
        binding.updateBtn.setOnClickListener{
            updateItem()
        }

        binding.btnCheckOut.setOnClickListener{
            findNavController().navigate(UpdateFragmentDirections.actionUpdateFragmentToPaymentFragment(args.currentCheckIn))
        }

        binding.btnCancel.setOnClickListener{
            findNavController().navigate(R.id.action_updateFragment_to_checkInListFragment)
        }




        return binding.root
    }

        private fun updateItem(){
            val name = updateGuestName.text.toString()
            val contact = updateGuestContact.text.toString()
            val email = updateGuestEmail.text.toString()
            val checkIn = args.currentCheckIn.checkInDate
            val checkOut = args.currentCheckIn.checkOutDate
            val roomType = args.currentCheckIn.roomType
            val roomNo = args.currentCheckIn.roomNo
            val adultNo = args.currentCheckIn.adultNo
            val childNo = args.currentCheckIn.childNo

            val updatedCheckIn = CheckIn(args.currentCheckIn.checkInId, checkIn, checkOut, name, contact, email, roomType, roomNo, adultNo, childNo)
            checkInViewModel.updateCheckIn(updatedCheckIn)

            findNavController().navigate(UpdateFragmentDirections.actionUpdateFragmentToUpdateFragment2(args.currentCheckIn))
        }


}