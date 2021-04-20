package com.example.assignment.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReservationViewModel(val database: reservationDao, application: Application) : AndroidViewModel(application) {

    private val readAllReservation: LiveData<List<Reservation>>
    private val repository: reservationRepository

    init {
        val reservationDao = reservationDatabase.getDatabase(application).reservationDao()
        repository = reservationRepository(reservationDao)
        readAllReservation = repository.readAllReservation
    }

    fun addReservation(reservation: Reservation){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addReservation(reservation)
        }
    }
}