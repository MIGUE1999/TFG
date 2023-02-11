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
    val inscriptionCost: String,
    val startDate: String,
    val endDate: String,
    val registrationDeadline : String,
    val prize: String,
    val poster: Bitmap?,

    val idOrganizer : Int
    //idReserva
)