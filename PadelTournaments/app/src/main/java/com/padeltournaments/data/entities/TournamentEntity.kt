package com.padeltournaments.data.entities

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "tournament")
data class TournamentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val category: String,
    val inscriptionCost: Int,
    val startDate: String,
    val endDate: String,
    val prize: Int,
    val poster: Bitmap? = null,

    val idOrganizer : Int,
    //idReserva
)