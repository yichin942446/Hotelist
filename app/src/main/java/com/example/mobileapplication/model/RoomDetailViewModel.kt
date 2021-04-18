package com.example.mobileapplication.model

import android.provider.SyncStateContract.Helpers.update
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapplication.RoomDetailFragment
import com.example.mobileapplication.database.CheckIn
import com.example.mobileapplication.database.CheckInDao
import com.example.mobileapplication.database.HotelRoom
import kotlinx.android.synthetic.main.fragment_room_detail.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.System.currentTimeMillis

class RoomDetailViewModel(
        private val checkInKey: Long = 0L,
        val database: CheckInDao) : ViewModel(){


    private val _navigateToGuestDetail = MutableLiveData<CheckIn>()

    val navigateToGuestDetail: LiveData<CheckIn>
        get() = _navigateToGuestDetail

    fun doneNavigating() {
        _navigateToGuestDetail.value = null
    }


    fun cancelCheckIn(){
        viewModelScope.launch {
            val checkIn = checkIn.value?: return@launch

            delete(checkIn)
        }
    }




    fun getHotelRooms(roomType: String): List<String> = runBlocking{

            getRooms(roomType)

    }

    private suspend fun getRooms(roomType: String): List<String>{
        val rooms = database.getHotelRoom(roomType)

        return rooms
    }


    private var checkIn = MutableLiveData<CheckIn?>()

    init {
        initializeCheckIn()
    }

    private fun initializeCheckIn() {
        viewModelScope.launch {
            checkIn.value = getCheckIn()
        }
    }

    private suspend fun getCheckIn(): CheckIn? {
        var check = database.getCheckIn()

        return check
    }

    private suspend fun update(checkIn: CheckIn) {
        database.update(checkIn)
    }

    private  suspend fun delete(checkIn: CheckIn){
        database.delete(checkIn)
    }

    fun onSetRoomDetail(adult : TextView, child: TextView, roomType: Spinner, roomNo: Spinner) {
        viewModelScope.launch {
            val checkIn = checkIn.value ?: return@launch
            //checkIn.roomType = fragment.room_type.selectedItem.toString()
            //checkIn.roomNo = fragment.room_no.selectedItem.toString()

            //checkIn.roomType = currentTimeMillis().toString()
            //val checkIn = CheckIn()
            //checkIn.roomType = fragment.room_type.selectedItem.toString()
            //checkIn.roomNo = fragment.room_no.selectedItem.toString()*/
            var childNo = 0
            var adultNo = 0

           if (child.text.toString() != "Drag the slide bar") {
              childNo = child.text.toString().toInt()
           }

            if(adult.text.toString() != "Drag the slide bar") {
                adultNo = adult.text.toString().toInt()
            }

            checkIn.childNo = childNo
            checkIn.adultNo = adultNo
            checkIn.roomType = roomType.selectedItem.toString()
            checkIn.roomNo = roomNo.selectedItem.toString()


            update(checkIn)

            // Setting this state variable to true will alert the observer and trigger navigation.
           // _navigateToSleepTracker.value = true

            _navigateToGuestDetail.value = checkIn



        }
        //val checkIn = CheckIn()
        //_navigateToGuestDetail.value = checkIn
    }



}