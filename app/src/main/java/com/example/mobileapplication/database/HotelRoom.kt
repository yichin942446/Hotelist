package com.example.mobileapplication.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "hotel_room_table")
data class HotelRoom (

    @PrimaryKey(autoGenerate = true)
    var roomId: Long = 0L,

    @ColumnInfo(name = "room_type")
    var addRoomType: String = "",

    @ColumnInfo(name = "room_no")
    var addRoomNo: String = ""

): Parcelable