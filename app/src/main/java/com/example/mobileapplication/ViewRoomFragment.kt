package com.example.mobileapplication

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobileapplication.database.CheckInDatabase
import com.example.mobileapplication.databinding.FragmentViewRoomBinding
import com.example.mobileapplication.model.CheckInViewModel
import com.example.mobileapplication.model.CheckInViewModelFactory

class ViewRoomFragment : Fragment() {

    private val args by navArgs<ViewRoomFragmentArgs>()
    private lateinit var checkInViewModel: CheckInViewModel

    private lateinit var binding: FragmentViewRoomBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewRoomBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = CheckInDatabase.getInstance(application).checkInDao

        val viewModelFactory = CheckInViewModelFactory(dataSource, application)

        checkInViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(CheckInViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel2 = checkInViewModel


        binding.txtRoomType.text = args.currentHotel.addRoomType
        binding.txtRoomNo.text = args.currentHotel.addRoomNo

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete){
            deleteRoom()
        }
        return super.onOptionsItemSelected(item)
    }

    fun deleteRoom(){
        val builder = AlertDialog.Builder(requireContext())

        builder.setPositiveButton("yes"){_,_->
            checkInViewModel.deleteHotelRoom(args.currentHotel)
            Toast.makeText(requireContext(), "Successfully removed: ${args.currentHotel.addRoomNo} room ", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_viewRoomFragment_to_roomListFragment2)
        }
        builder.setNegativeButton("no"){_,_-> }
        builder.setTitle("Delete ${args.currentHotel.addRoomNo}?")
        builder.setMessage("Are you sure you want to delete ${args.currentHotel.addRoomNo} room?")
        builder.create().show()
    }

}