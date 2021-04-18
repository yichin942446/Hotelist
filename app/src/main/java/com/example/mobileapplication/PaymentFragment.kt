package com.example.mobileapplication

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobileapplication.database.CheckInDatabase
import com.example.mobileapplication.databinding.FragmentPaymentBinding
import com.example.mobileapplication.model.CheckInViewModel
import com.example.mobileapplication.model.CheckInViewModelFactory

class PaymentFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var binding: FragmentPaymentBinding
    private lateinit var checkInViewModel: CheckInViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = CheckInDatabase.getInstance(application).checkInDao

        val viewModelFactory = CheckInViewModelFactory(dataSource, application)

        checkInViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(CheckInViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel = checkInViewModel

        binding.btnPay.setOnClickListener{
            deleteCheckIn()
        }

        binding.btnCancel.setOnClickListener{
            findNavController().navigate(R.id.action_paymentFragment_to_checkInListFragment2)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

            val roomPrice = when (args.currentCheckIn.roomType.toString()) {
                "Regular" -> 100
                "Standard"-> 130
                "Luxury" -> 200
                "President" -> 500
                else -> 100
            }

            val totalPrice = (roomPrice * args.currentCheckIn.adultNo) + ((roomPrice/2) * args.currentCheckIn.childNo)

            val price = "RM " +  totalPrice.toString() + " sub total"
            binding.subTotal.text = price

    }


    fun deleteCheckIn(){

        var pattern = Regex("[0-9]{3,4}")

        if (binding.txtCardNo.text.toString().isEmpty()) {
            binding.txtCardNo.error = "Please enter card number"
            binding.txtCardNo.requestFocus()
            return
        }
        if (binding.txtValidDate.text.toString().isEmpty()) {
            binding.txtValidDate.error = "Please enter card expiry date"
            binding.txtValidDate.requestFocus()
            return
        }
        if (!pattern.containsMatchIn(binding.txtCvv.text.toString())) {
            binding.txtCvv.error = "Please enter 3 digit number"
            binding.txtCvv.requestFocus()
            return
        }
        if (binding.txtCvv.text.toString().isEmpty()) {
            binding.txtCvv.error = "Please enter cvv"
            binding.txtCvv.requestFocus()
            return
        }

        val builder = AlertDialog.Builder(requireContext())

        builder.setPositiveButton("yes"){_,_->
            checkInViewModel.deleteCheckIn(args.currentCheckIn)
            Toast.makeText(requireContext(), "Successfully checked out: ${args.currentCheckIn.guestName}", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_paymentFragment_to_checkInListFragment)
        }
        builder.setNegativeButton("no"){_,_-> }
        builder.setTitle("Check out ${args.currentCheckIn.guestName}?")
        builder.setMessage("Are you sure you want to check out ${args.currentCheckIn.guestName}?")
        builder.create().show()
    }
}