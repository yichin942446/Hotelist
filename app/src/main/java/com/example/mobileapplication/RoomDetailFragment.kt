package com.example.mobileapplication

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mobileapplication.database.CheckInDatabase
import com.example.mobileapplication.databinding.FragmentRoomDetailBinding
import com.example.mobileapplication.model.CheckInViewModel
import com.example.mobileapplication.model.CheckInViewModelFactory
import com.example.mobileapplication.model.RoomDetailViewModel
import com.example.mobileapplication.model.RoomDetailViewModelFactory
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.fragment_room_detail.*
import kotlinx.android.synthetic.main.fragment_room_detail.room_type


class RoomDetailFragment : Fragment() {

    private lateinit var binding: FragmentRoomDetailBinding
    private var track: Boolean = false

    var totalPrice = 0

    private val sharedViewModel: CheckInViewModel by activityViewModels()
    private lateinit var roomDetailViewModel: RoomDetailViewModel
    private var roomNoItems: List<String> = listOf("Hi")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentRoomDetailBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val arguments = RoomDetailFragmentArgs.fromBundle(requireArguments())

        val dataSource = CheckInDatabase.getInstance(application).checkInDao

        val viewModelFactory = RoomDetailViewModelFactory(arguments.checkInKey, dataSource)

        roomDetailViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(RoomDetailViewModel::class.java)
        //binding.setLifecycleOwner(this)*/
        binding.viewModel = roomDetailViewModel

        binding.setLifecycleOwner(this)


        roomDetailViewModel.navigateToGuestDetail.observe(viewLifecycleOwner, Observer {currentCheckIn ->
            currentCheckIn?.let {
                this.findNavController().navigate(
                        RoomDetailFragmentDirections
                                .actionRoomDetailFragmentToGuestDetailFragment(currentCheckIn.checkInId))

                roomDetailViewModel.doneNavigating()
            }
        })

        binding.checkinNextBtn.setOnClickListener({
            validateGuestNo()
        })

        //android:onClick="@{() -> viewModel.onSetRoomDetail(txtAdultSlider, txtChildSlider, roomType, roomNo)}"

        binding.btnCancel.setOnClickListener{
            roomDetailViewModel.cancelCheckIn()
            findNavController().navigate(R.id.action_roomDetailFragment_to_checkInOptionFragment2)
        }


        return binding.root
    }

    private fun validateGuestNo(){
        var adultCount = 0
        var childCount = 0

        if(binding.txtAdultSlider.text.toString() != "Drag the slide bar") {
            adultCount = binding.txtAdultSlider.text.toString().toInt()
        }

        if(binding.txtChildSlider.text.toString() != "Drag the slide bar"){
            childCount = binding.txtChildSlider.text.toString().toInt()
        }


        val totalCount = adultCount + childCount

        if (totalCount > 0 && track) {
            roomDetailViewModel.onSetRoomDetail(binding.txtAdultSlider, binding.txtChildSlider, binding.roomType, binding.roomNo)
        } else if(totalCount == 0) {
            Toast.makeText(requireContext(), "Must have at least one child or adult", Toast.LENGTH_LONG).show()
            binding.txtPrice.text = "Must have at least one child or adult"
        }else if(track == false){
            Toast.makeText(requireContext(), "This room type don't have available room. Please select another room type", Toast.LENGTH_LONG).show()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        selectAdultNoWithSlider()
        selectChildNoWithSlider()
        selectRoomType()
        selectRoomNo()



        binding?.apply {

            //txtTesting.text = viewModel.getCheckIn()
            /*checkinNextBtn.setOnClickListener { view: View ->
                view.findNavController()
                    .navigate(R.id.action_roomDetailFragment_to_guestDetailFragment)
            }*/
            //viewModel = sharedViewModel
        }


    }

    /*private fun startNextActivity() {
        val intent = Intent(requireContext(), StayDetail::class.java)
        startActivity(intent)
    }*/

    private fun selectChildNoWithSlider() {
        binding.childSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                Log.d("onStartTrackingTouch", slider.value.toString())
                //val txtAdultSlider: TextView = findViewById(R.id.txt_adult_slider)
                binding.txtChildSlider.text = slider.value.toInt().toString()
                binding.txtChildSlider.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_child, 0, 0, 0);
            }

            override fun onStopTrackingTouch(slider: Slider) {
                Log.d("onStopTrackingTouch", slider.value.toString())
                //val txtAdultSlider: TextView = findViewById(R.id.txt_adult_slider)
                binding.txtChildSlider.text = slider.value.toInt().toString()
                binding.txtChildSlider.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_child, 0, 0, 0);
                setPrice()
            }
        })
    }

    private  fun setPrice(){
        if(!binding.roomType.toString().isEmpty() && binding.txtAdultSlider.text.toString() != "Drag the slide bar" && binding.txtChildSlider.text.toString() != "Drag the slide bar"){
            val roomPrice = when (binding.roomType.selectedItem.toString()) {
                "Regular" -> 100
                "Standard"-> 130
                "Luxury" -> 200
                "President" -> 500
                else -> 100
            }

            totalPrice = (roomPrice * binding.txtAdultSlider.text.toString().toInt()) + ((roomPrice/2) * binding.txtChildSlider.text.toString().toInt())

            val price = "RM " + totalPrice.toString() + "sub total"
            binding.txtPrice.text = price
        }
    }

    private fun selectAdultNoWithSlider() {
        //val slider: Slider = findViewById(R.id.adult_slider)

        binding.adultSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                Log.d("onStartTrackingTouch", slider.value.toString())
                //val txtAdultSlider: TextView = findViewById(R.id.txt_adult_slider)
                binding.txtAdultSlider.text = slider.value.toInt().toString()
                binding.txtAdultSlider.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_adult, 0, 0, 0);
            }

            override fun onStopTrackingTouch(slider: Slider) {
                Log.d("onStopTrackingTouch", slider.value.toString())
                //val txtAdultSlider: TextView = findViewById(R.id.txt_adult_slider)
                binding.txtAdultSlider.text = slider.value.toInt().toString()
                binding.txtAdultSlider.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_adult, 0, 0, 0);
                setPrice()

            }
        })
    }

    /*private fun selectChildNo() {
        //option = findViewById(R.id.child_no) as Spinner
        val items = listOf(1, 2, 3, 4)
        binding.childNo.adapter = ArrayAdapter<Int>(requireContext(), android.R.layout.simple_list_item_1, items)

        binding.childNo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {

                //txtAdultNo.text = items.get(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //txtAdultNo.text = "Please select an option"

            }
        }
    }*/

    /*private fun selectAdultNo() {
        //option = findViewById(R.id.adult_no) as Spinner
        val items = listOf(1, 2, 3, 4)
        binding.adultNo.adapter = ArrayAdapter<Int>(requireContext(), android.R.layout.simple_list_item_1, items)

        binding.adultNo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {

                //binding.txtAdultNo.text = items.get(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //binding.txtAdultNo.text = "Please select an option"

            }
        }
    }*/

    private  fun setRoomNo(){
        roomNoItems = roomDetailViewModel.getHotelRooms(binding.roomType.selectedItem.toString())
        if (roomNoItems.isNullOrEmpty()) {

            track = false
            selectRoomNo()
        }

    }

    private fun selectRoomType() {

        val items = listOf("Regular", "Standard", "Luxury", "President")
        binding.roomType.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, items)

        binding.roomType.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                //binding.txtRoomType.text = items.get(position)

                val selectedItem = parent?.getItemAtPosition(position).toString()
                roomNoItems = roomDetailViewModel.getHotelRooms(selectedItem)
                selectRoomNo()
                setRoomNo()
                setPrice()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //binding.txtRoomType.text = "Please select an option"

            }
        }
    }

    private fun selectRoomNo() {
        //val items = listOf("R101", "R102", "R103", "R104", "R105", "R106", "R107", "R108")



        binding.roomNo.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, roomNoItems)


        binding.roomNo.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                //binding.txtRoomType.text = items.get(position)


                    track = true


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //binding.txtRoomType.text = "Please select an option"

                    track = false


            }
        }
    }
}