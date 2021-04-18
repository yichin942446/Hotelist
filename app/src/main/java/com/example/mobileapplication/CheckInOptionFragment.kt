package com.example.mobileapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.mobileapplication.databinding.FragmentCheckInOptionBinding
import com.example.mobileapplication.model.CheckInViewModel

class CheckInOptionFragment : Fragment() {
    private lateinit var binding: FragmentCheckInOptionBinding

    private val sharedViewModel: CheckInViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  FragmentCheckInOptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.checkinGuest.setOnClickListener{view: View ->
            view.findNavController().navigate(R.id.action_checkInOptionFragment2_to_stayDetailFragment2)
        }

        binding.checkinList.setOnClickListener{view: View ->
            view.findNavController().navigate(R.id.action_checkInOptionFragment2_to_checkInListFragment)
        }

        binding.btnHome.setOnClickListener({
            startActivity(Intent(requireContext(), HomeActivity::class.java))

        })
    }

    companion object {

    }
}