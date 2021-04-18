package com.example.mobileapplication

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mobileapplication.database.CheckIn
import com.example.mobileapplication.database.CheckInDatabase
import com.example.mobileapplication.databinding.FragmentStayDetailBinding
import com.example.mobileapplication.model.CheckInViewModel
import com.example.mobileapplication.model.CheckInViewModelFactory
import kotlinx.android.synthetic.main.fragment_stay_detail.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*


class StayDetailFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: FragmentStayDetailBinding

    //private val sharedViewModel: CheckInViewModel by activityViewModels()

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
    var validate = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStayDetailBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = CheckInDatabase.getInstance(application).checkInDao

        val viewModelFactory = CheckInViewModelFactory(dataSource, application)

        val checkInViewModel =
            ViewModelProvider(
                this, viewModelFactory).get(CheckInViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel = checkInViewModel

        binding.setLifecycleOwner(this)



        checkInViewModel.navigateToRoomDetail.observe(viewLifecycleOwner, Observer {currentCheckIn ->
            currentCheckIn?.let {
                this.findNavController().navigate(
                        StayDetailFragmentDirections
                                .actionStayDetailFragment2ToRoomDetailFragment(currentCheckIn.checkInId))

                checkInViewModel.doneNavigating()
            }
        })

        binding.btnCancel.setOnClickListener{
            findNavController().navigate(R.id.action_stayDetailFragment2_to_checkInOptionFragment2)
        }

        setHasOptionsMenu(true)

        return binding.root



    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        pickCheckInDate()

        pickCheckOutDate()

        binding?.stayDetailFragment = this
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

    private fun pickCheckInDate() {
        binding.calCheckin.setOnClickListener{
            count = 1
            getDateCalendar()
            DatePickerDialog(requireContext(),this, year, month, day).show()
        }
    }

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

        if(count == 1){
            val date = "$savedDay-$savedMonth-$savedYear"
            binding.checkinDate.text = date
            date1 = date
            from = LocalDate.parse(date1, DateTimeFormatter.ofPattern("d-M-yyyy"))
        }
        else if(count == 2){
            val date = "$savedDay2-$savedMonth2-$savedYear2"
            binding.checkoutDate.text = date
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
        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.BASIC_ISO_DATE
        val formatted = current.format(formatter)
        val currentDate = LocalDate.parse(formatted, DateTimeFormatter.ofPattern("yyyyMMdd"))

        // calculate the period between those two
        period = Period.between(from, today).getDays()
        validate = Period.between(currentDate, from).getDays()

        binding.noOfNight.text = period.toString()
        // and print it in a human-readable way
        /*println("The difference between " + from.format(DateTimeFormatter.ISO_LOCAL_DATE)
            + " and " + today.format(DateTimeFormatter.ISO_LOCAL_DATE) + " is "
            + period.getYears() + " years, " + period.getMonths() + " months and "
            + period.getDays() + " days")*/


    }

    fun onStayDetail(){
        val checkIn = CheckIn()
        checkIn.checkInDate = date1
        checkIn.checkOutDate = date2
        //sharedViewModel.insert(date1, date2)
        //textView2.text = binding.checkinDate.text.toString()
        //findNavController().navigate(R.id.action_stayDetailFragment2_to_roomDetailFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.logout){
            startActivity(Intent(requireContext(), SignInActivity::class.java))
        }
        return super.onOptionsItemSelected(item)

    }


}