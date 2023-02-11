package com.padeltournaments.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inscription")
data class InscriptionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    //val idPareja: Int,
    //idPago
    //idTorneoInscrito
    //val nombreUsuarioPagador
)