package com.android.bg_farmers.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.bg_farmers.data.models.Farmer

@Database(
    entities = [Farmer::class],
    version = 1,
    exportSchema = false
)
abstract class FarmerDatabase: RoomDatabase() {
    abstract fun farmerDao(): FarmerDAO
}