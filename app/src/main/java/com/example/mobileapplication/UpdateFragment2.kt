package com.example.mobileapplication

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mobileapplication.database.CheckIn
import com.example.mobileapplication.database.CheckInDatabase
import com.example.mobileapplication.databinding.FragmentUpdate2Binding
import com.example.mobileapplication.databinding.FragmentUpdateBinding
import com.example.mobileapplication.model.*
import kotlinx.android.synthetic.main.fragment_update.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*


class UpdateFragment2 : DialogFragment(), DatePickerDialog.OnDateSetListener{

    private lateinit var binding: FragmentUpdate2Binding
    private lateinit var checkInViewModel: CheckInViewModel


    private val args by navArgs<UpdateFragmentArgs>()

    var day = 0
    var month = 0
    var year = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0

    var day2 = 0
    var month2 = 0
    var year2 = 0

    var savedDay2 = 0
    var savedMonth2 = 0
    var savedYear2 = 0

    var count = 0

    var date1 = ""
    var date2 = ""
    var d1 = ""
    var d2 = ""
    var period = 0
    @RequiresApi(Build.VERSION_CODES.O)
    var from = LocalDate.now()
    @RequiresApi(Build.VERSION_CODES.O)
    var today = LocalDate.now()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUpdate2Binding.inflate(inflater, container, false)

        /*val application = requireNotNull(this.activity).application

        val arguments = UpdateFragment2Args.fromBundle(arguments!!)

        val dataSource = CheckInDatabase.getInstance(application).checkInDao

        val viewModelFactory = UpdateViewModelFactory2(arguments.checkInKey, dataSource)

        val updateViewModel2 =
            ViewModelProvider(
                this, viewModelFactory).get(UpdateViewModel2::class.java)*/

        val application = requireNotNull(this.activity).application

        val dataSource = CheckInDatabase.getInstance(application).checkInDao

        val viewModelFactory = CheckInViewModelFactory(dataSource, application)

        checkInViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(CheckInViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel = checkInViewModel

        //binding.viewModel = updateViewModel2

        binding.setLifecycleOwner(this)

        checkInViewModel.navigateToCheckInList.observe(viewLifecycleOwner, Observer { currentCheckIn ->
            currentCheckIn?.let {
                this.findNavController().navigate(
                   UpdateFragment2Directions.actionUpdateFragment2ToCheckInListFragment(currentCheckIn.checkInId))

                checkInViewModel.doneNavigatingUpdate()
            }
        })

        //android:onClick="@{() -> viewModel.updateCheckIn(checkinDate, checkoutDate)}"

        //val checkOut = args.currentCheckIn.checkOutDate
        //val checkOutDate = LocalDate.parse(checkOut, DateTimeFormatter.ofPattern("d-M-yyyy"))



        binding.updateBtn.setOnClickListener{

            update()
        }

        binding.btnCancel.setOnClickListener{
            findNavController().navigate(R.id.action_updateFragment2_to_checkInListFragment)
        }


        return binding.root
    }



    private fun update(){

        val newCheckOut = binding.newCheckoutDate.text.toString()

        var newCheckOutDate = LocalDate.now()

        if(!newCheckOut.isEmpty()) {
             newCheckOutDate = LocalDate.parse(newCheckOut, DateTimeFormatter.ofPattern("d-M-yyyy"))
        }

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.BASIC_ISO_DATE
        val formatted = current.format(formatter)
        val currentDate = LocalDate.parse(formatted, DateTimeFormatter.ofPattern("yyyyMMdd"))

        val validate = Period.between(currentDate, newCheckOutDate ).getDays()

        val name = args.currentCheckIn.guestName
        val contact = args.currentCheckIn.guestContact
        val email = args.currentCheckIn.guestEmail
        val checkIn = args.currentCheckIn.checkInDate
        val checkOut = binding.newCheckoutDate.text.toString()
        val roomType = args.currentCheckIn.roomType
        val roomNo = args.currentCheckIn.roomNo
        val adultNo = args.currentCheckIn.adultNo
        val childNo = args.currentCheckIn.childNo

        val updatedCheckIn = CheckIn(args.currentCheckIn.checkInId, checkIn, checkOut, name, contact, email, roomType, roomNo, adultNo, childNo)
        if(validate > 0){
            checkInViewModel.updateCheckIn(updatedCheckIn)
            findNavController().navigate(R.id.action_updateFragment2_to_checkInListFragment)
        }else{
            Toast.makeText(requireContext(), "Invalid check out date", Toast.LENGTH_LONG).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.oldCheckOutDate.text = args.currentCheckIn.checkOutDate.toString()
        //pickCheckInDate()

        pickCheckOutDate()

        //binding?.UpdateFragment2 = this
        /*binding?.apply{
            stayBtn.setOnClickListener{view: View ->
               view.findNavController().navigate(R.id.action_stayDetailFragment2_to_roomDetailFragment)
                //onStayDetail()
            }
            //textView2.text = binding.checkinDate.text.toString()
            //viewModel = sharedViewModel
            //lifecycleOwner = viewLifecycleOwner
            //stayDetailFragment = this@StayDetailFragment
        }*/

    }

    /*override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it
        return DatePickerDialog(requireContext(), this, year, month, day)

    }*/


    private fun getDateCalendar(){
        val cal: Calendar = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)

    }

    private fun getCheckOutDateCalendar(){
        val cal2: Calendar = Calendar.getInstance()
        day2 = cal2.get(Calendar.DAY_OF_MONTH)
        month2 = cal2.get(Calendar.MONTH)
        year2 = cal2.get(Calendar.YEAR)

    }

    /*private fun pickCheckInDate() {
        binding.calCheckin.setOnClickListener{
            count = 1
            getDateCalendar()
            DatePickerDialog(requireContext(),this, year, month, day).show()
        }
    }*/

    private fun pickCheckOutDate() {
        binding.calCheckout.setOnClickListener{
            getCheckOutDateCalendar()
            DatePickerDialog(requireContext(),this, year2, month2, day2).show()
            count = 2
        }
    }

    companion object {

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        savedDay = dayOfMonth
        savedMonth = month + 1
        savedYear = year

        savedDay2 = dayOfMonth
        savedMonth2 = month + 1
        savedYear2 = year

        /*if(count == 1){
            val date = "$savedDay-$savedMonth-$savedYear"
            binding.checkinDate.text = date
            date1 = date
            from = LocalDate.parse(date1, DateTimeFormatter.ofPattern("d-M-yyyy"))
        }*/
        if(count == 2){
            val date = "$savedDay2-$savedMonth2-$savedYear2"
            binding.newCheckoutDate.text = date
            date2 = date
            //sharedViewModel.setCheckOutDate(date)
            today = LocalDate.parse(date2, DateTimeFormatter.ofPattern("d-M-yyyy"))
        }

        //val formatDate  = SimpleDateFormat("dd-MM-yyyy")

        //d1 = formatDate.parse(date1)
        //d2 = formatDate.parse(date2)
        //if (date1 != null && date2 != null) {
        // parse the date with a suitable formatter

        // get today's date


        // calculate the period between those two
        period = Period.between(from, today).getDays()

        //binding.noOfNight.text = period.toString()
        // and print it in a human-readable way
        /*println("The difference between " + from.format(DateTimeFormatter.ISO_LOCAL_DATE)
            + " and " + today.format(DateTimeFormatter.ISO_LOCAL_DATE) + " is "
            + period.getYears() + " years, " + period.getMonths() + " months and "
            + period.getDays() + " days")*/


    }
}