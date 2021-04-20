package com.example.assignment.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface reservationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addReservation(reservation: Reservation)

    @Query("SELECT * FROM reservation_table ORDER BY id ASC")
    fun readAllReservation(): LiveData<List<Reservation>>
}