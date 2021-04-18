package com.example.mobileapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CheckIn::class, HotelRoom::class], version = 1, exportSchema = false)
abstract class CheckInDatabase : RoomDatabase(){

    abstract val checkInDao: CheckInDao

    companion object {

        @Volatile
        private var INSTANCE: CheckInDatabase? = null

        fun getInstance(context: Context): CheckInDatabase {

            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CheckInDatabase::class.java,
                        "checkin_database")

                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }


                return instance
            }
        }
    }
}