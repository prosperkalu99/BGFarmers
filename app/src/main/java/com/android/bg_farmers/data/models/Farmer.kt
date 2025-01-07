package com.android.bg_farmers.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.bg_farmers.util.Constants

@Entity(
    tableName = Constants.DATABASE_TABLE
)
data class Farmer(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val cropType: String,
)
