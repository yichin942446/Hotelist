package com.example.assignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [occupiedRoom::class, Reservation::class], version = 1, exportSchema = false)
abstract class reservationDatabase:RoomDatabase() {

    abstract fun reservationDao(): reservationDao

    companion object{
        @Volatile
        private var INSTANCE: reservationDatabase? = null

        fun getDatabase(context: Context): reservationDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    reservationDatabase::class.java,
                    "reservationDatabase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}