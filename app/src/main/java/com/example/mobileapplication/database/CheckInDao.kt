package com.example.mobileapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CheckInDao {

    @Insert
    suspend fun  insert(checkIn: CheckIn)

    @Insert
    suspend fun insertRoom(room: HotelRoom)

    @Update
    suspend fun update(checkIn: CheckIn)

    @Delete
    suspend fun  delete(checkIn: CheckIn)

    @Delete
    suspend fun deleteRoom(room: HotelRoom)

    @Query("DELETE FROM check_in_table")
    fun clear()

    @Query("SELECT * FROM check_in_table ORDER BY checkInId DESC")
    fun getCheckInList(): LiveData<List<CheckIn>>

    @Query("SELECT * FROM hotel_room_table ORDER BY room_type")
    fun getAllRooms(): LiveData<List<HotelRoom>>

    @Query("SELECT * FROM check_in_table ORDER BY checkInId DESC LIMIT 1")
    suspend fun getCheckIn(): CheckIn?

    @Query("SELECT * from check_in_table WHERE checkInId = :key")
    suspend fun get(key: Long): CheckIn?


    @Query("SELECT room_no from hotel_room_table WHERE room_type = :room")
    suspend fun  getHotelRoom(room: String): List<String>



}