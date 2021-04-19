package com.example.mobileapplication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobileapplication.database.CheckInDatabase
import com.example.mobileapplication.databinding.FragmentGuestDetailBinding
import com.example.mobileapplication.model.CheckInViewModel
import com.example.mobileapplication.model.CheckInViewModelFactory


class GuestDetailFragment : Fragment() {

    private lateinit var binding: FragmentGuestDetailBinding
    private lateinit var checkInViewModel: CheckInViewModel

    private val sharedViewModel: CheckInViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        binding = FragmentGuestDetailBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = CheckInDatabase.getInstance(application).checkInDao

        val viewModelFactory = CheckInViewModelFactory(dataSource, application)

        checkInViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(CheckInViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel = checkInViewModel

        binding.setLifecycleOwner(this)

        checkInViewModel.navigateToCheckList.observe(viewLifecycleOwner, Observer {currentCheckIn ->
            currentCheckIn?.let {
                this.findNavController().navigate(
                        GuestDetailFragmentDirections
                                .actionGuestDetailFragmentToCheckInListFragment(currentCheckIn.checkInId))

                checkInViewModel.doneNavigating2()
            }
        })

        //android:onClick="@{() -> viewModel.onSetGuestDetail(guestName, guestContact, guestEmail)}"

        binding.guestNextBtn.setOnClickListener({
            onSetGuestDetail()
        })

        binding.btnCancel.setOnClickListener{
            checkInViewModel.cancelCheckIn()
            findNavController().navigate(R.id.action_guestDetailFragment_to_checkInOptionFragment2)
        }

        return binding.root
    }

    private fun onSetGuestDetail() {

        val pattern = Regex("^01[0-9]{8}$")

        if (binding.guestName.text.toString().isEmpty()) {
            binding.guestName.error = "Please enter guest name"
            binding.guestName.requestFocus()
            return
        }

        if (binding.guestContact.text.toString().isEmpty()) {
            binding.guestContact.error = "Please enter guest contact"
            binding.guestContact.requestFocus()
            return
        }

        if (binding.guestEmail.text.toString().isEmpty()) {
            binding.guestEmail.error = "Please enter email"
            binding.guestEmail.requestFocus()
            return
        }

        if (!pattern.containsMatchIn(binding.guestContact.text.toString())) {
            binding.guestContact.error = "Please follow format : 01xxxxxxxx"
            binding.guestContact.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(binding.guestEmail.text.toString()).matches()) {
            binding.guestEmail.error = "Please enter valid email"
            binding.guestEmail.requestFocus()
            return
        }


        checkInViewModel.onSetGuestDetail(binding.guestName, binding.guestContact, binding.guestEmail)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*binding?.apply {
            sharedViewModel.setGuestName(guestName.text.toString())
            sharedViewModel.setGuestContact(guestContact.text.toString())
            sharedViewModel.setGuestEmail(guestEmail.text.toString())

            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner

            //guestNextBtn.setOnClickListener { startNextActivity() }
            guestNextBtn.setOnClickListener { view: View ->
                view.findNavController()
                        .navigate(R.id.action_guestDetailFragment_to_checkInListFragment)
            }
        }*/

    }

    private fun startNextActivity() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)
    }


}