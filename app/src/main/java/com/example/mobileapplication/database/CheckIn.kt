package com.example.mobileapplication.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "check_in_table")
data class CheckIn (

    @PrimaryKey(autoGenerate = true)
    var checkInId: Long = 0L,

    @ColumnInfo(name = "check_in_date")
    var checkInDate: String = "",

    @ColumnInfo(name = "check_out_date")
    var checkOutDate: String = "",

    @ColumnInfo(name = "guest_name")
    var guestName: String = "",

    @ColumnInfo(name = "guest_contact")
    var guestContact: String = "",

    @ColumnInfo(name = "guest_email")
    var guestEmail: String = "",

    @ColumnInfo(name = "room_type")
    var roomType: String = "",

    @ColumnInfo(name = "room_no")
    var roomNo: String = "",

    @ColumnInfo(name = "adult_no")
    var adultNo: Int = -1,

    @ColumnInfo(name = "child_no")
    var childNo : Int = -1

): Parcelable