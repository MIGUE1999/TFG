package com.padeltournaments.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "court")
data class CourtEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val ubication: String,
    val courtNumber: String
)

