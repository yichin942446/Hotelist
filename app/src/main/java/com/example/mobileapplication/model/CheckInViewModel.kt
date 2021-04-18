package com.example.mobileapplication.model

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Application
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mobileapplication.AddRoomFragment
import com.example.mobileapplication.R
import com.example.mobileapplication.StayDetailFragment
import com.example.mobileapplication.UpdateFragmentArgs
import com.example.mobileapplication.database.CheckIn
import com.example.mobileapplication.database.CheckInDao
import com.example.mobileapplication.database.HotelRoom
import kotlinx.android.synthetic.main.fragment_add_room.*
import kotlinx.android.synthetic.main.fragment_stay_detail.*
import kotlinx.coroutines.launch

class CheckInViewModel(
    val database: CheckInDao,
    application: Application) : AndroidViewModel(application)  {


    private val _navigateToCheckInList = MutableLiveData<CheckIn>()

    val navigateToCheckInList: LiveData<CheckIn>
        get() = _navigateToCheckInList

    fun doneNavigatingUpdate() {
        _navigateToCheckInList.value = null
    }


    //from stay fragment to room fragment
    private val _navigateToRoomDetail = MutableLiveData<CheckIn>()

    val navigateToRoomDetail: LiveData<CheckIn>
        get() = _navigateToRoomDetail

    fun doneNavigating() {
        _navigateToRoomDetail.value = null
    }

    //from guest to check in list
    private val _navigateToCheckList = MutableLiveData<CheckIn>()

    val navigateToCheckList: LiveData<CheckIn>
        get() = _navigateToCheckList

    fun doneNavigating2() {
        _navigateToCheckList.value = null
    }

    //from update1 to update2
    private val _navigateToUpdate2 = MutableLiveData<CheckIn>()

    val navigateToUpdate2: LiveData<CheckIn>
        get() = _navigateToUpdate2

    fun doneNavigating3() {
        _navigateToUpdate2.value = null
    }

    //trigger navigation to list fragment
    fun onSetGuestDetail(name: TextView, contact: TextView, email: TextView){
        viewModelScope.launch {
            val checkIn = currentCheckIn.value ?: return@launch


            checkIn.guestName = name.text.toString()
            checkIn.guestContact = contact.text.toString()
            checkIn.guestEmail = email.text.toString()

            update(checkIn)

            Toast.makeText(getApplication(), "Successfully checked in", Toast.LENGTH_LONG ).show()
            _navigateToCheckList.value = checkIn
        }


    }

    fun cancelCheckIn(){
        viewModelScope.launch {
            val checkIn = currentCheckIn.value?: return@launch

            delete(checkIn)
        }
    }


    fun updateCheckIn(checkIn: CheckIn){
        viewModelScope.launch {
            //val check = database.get(checkIn.checkInId)

            //check.guestName = checkIn.guestName.text.toString()


            update(checkIn)
        }

    }

    private suspend fun update(checkIn: CheckIn) {
        database.update(checkIn)
    }

    fun deleteCheckIn(checkIn: CheckIn){
        viewModelScope.launch {
            delete(checkIn)
        }
    }

    private  suspend fun delete(checkIn: CheckIn){
        database.delete(checkIn)
    }

    fun deleteHotelRoom(room: HotelRoom){
        viewModelScope.launch {
            deleteRoom(room)
        }
    }

    private suspend fun deleteRoom(room: HotelRoom){
        database.deleteRoom(room)
    }

    private var currentCheckIn = MutableLiveData<CheckIn?>()

    init {
        initializeCheckIn()
    }

    private fun initializeCheckIn() {
        viewModelScope.launch {
            currentCheckIn.value = getCheckInFromDatabase()
        }
    }

    private suspend fun getCheckInFromDatabase(): CheckIn? {
        val checkIn = database.getCheckIn()

        return checkIn
    }


    fun insert(fragment: StayDetailFragment){
        viewModelScope.launch {
            val checkIn = CheckIn()

            if(fragment.period > 0 && fragment.validate >= 0 ) {
                checkIn.checkInDate = fragment.date1
                checkIn.checkOutDate = fragment.date2

                insert(checkIn)
                _navigateToRoomDetail.value = checkIn
            }else if(fragment.validate < 0 ){

                fragment.noOfNight.text = "Check in date not valid."
            }
            else{
                fragment.noOfNight.text = "Check out date cannot same \nor before check in date."

            }


        }

    }

    private suspend fun insert(checkIn: CheckIn) {
        database.insert(checkIn)
    }

    val checkIns = database.getCheckInList()

    val hotelRooms = database.getAllRooms()


    fun addHotelRoom(fragment: AddRoomFragment){
        viewModelScope.launch {
            val room = HotelRoom()

            room.addRoomNo = fragment.add_roomNo.text.toString()
            room.addRoomType = fragment.add_room_type.selectedItem.toString()

            insertRoom(room)
        }
    }

    private suspend fun insertRoom(room: HotelRoom){
        database.insertRoom(room)
    }

    val roomType = MutableLiveData<String>()


}