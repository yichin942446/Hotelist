package com.example.mobileapplication.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobileapplication.database.CheckInDao

class RoomDetailViewModelFactory (
        private val checkInKey: Long,
        private val dataSource: CheckInDao) : ViewModelProvider.Factory {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RoomDetailViewModel::class.java)) {
                return RoomDetailViewModel(checkInKey, dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
