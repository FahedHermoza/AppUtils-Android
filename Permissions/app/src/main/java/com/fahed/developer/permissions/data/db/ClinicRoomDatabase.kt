package com.fahedhermoza.developer.examplenote01.Models

import android.app.Application
import android.provider.ContactsContract
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(Clinic::class), version = 1, exportSchema = false)
abstract class ClinicRoomDatabase : RoomDatabase() {

    abstract fun clinicsDao(): ClinicDao
    companion object {
        private val lock = Any()
        private const val DB_NAME = "clinics_database"
        private var INSTANCE: ClinicRoomDatabase? = null

        fun getInstance(application: Application): ClinicRoomDatabase {
            synchronized(ClinicRoomDatabase.lock) {
                if (ClinicRoomDatabase.INSTANCE == null) {
                    ClinicRoomDatabase.INSTANCE =
                        Room.databaseBuilder(application, ClinicRoomDatabase::class.java, ClinicRoomDatabase.DB_NAME)
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}