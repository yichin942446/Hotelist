package com.example.assignment.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "occupiedRoom")
data class occupiedRoom(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val occupiedRoomNo: String,
        val occupiedDate: String,
)