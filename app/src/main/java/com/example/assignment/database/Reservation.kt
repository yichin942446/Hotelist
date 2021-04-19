package com.example.assignment.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservation_table")
data class Reservation(
        @PrimaryKey(autoGenerate = true)
        val id: Int,

        @ColumnInfo(name = "name")
        val name: String,

        @ColumnInfo(name = "contact")
        val contact: String,

        @ColumnInfo(name = "date")
        val date: String,

        @ColumnInfo(name = "check_in_time")
        val checkInTime: String,

        @ColumnInfo(name = "number_of_room")
        val roomNo: String,

        @ColumnInfo(name = "number_of_adult")
        val adultNo: String,

        @ColumnInfo(name = "number_of_children")
        val childrenNo: String,

        @ColumnInfo(name = "room_1_list")
        val roomList1: String,

        @ColumnInfo(name = "room_2_list")
        val roomList2: String,

        @ColumnInfo(name = "room_3_list")
        val roomList3: String,

        @ColumnInfo(name = "all_room_list")
        val roomListAll: String,
)