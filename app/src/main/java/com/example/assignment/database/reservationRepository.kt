package com.example.assignment.database

import androidx.lifecycle.LiveData

class reservationRepository(private val reservationDao: reservationDao) {

    val readAllReservation: LiveData<List<Reservation>> = reservationDao.readAllReservation()

    suspend fun addReservation(reservation: Reservation) {
        reservationDao.addReservation(reservation)
    }
}