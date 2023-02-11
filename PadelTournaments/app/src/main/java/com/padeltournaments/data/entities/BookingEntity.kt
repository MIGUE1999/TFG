package com.padeltournaments.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "booking")
data class BookingEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    //val idPista: Int
    val startDate : String,
    val endDate : String
)
